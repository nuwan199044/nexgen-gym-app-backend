package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.common.AppConstants;
import com.enzith.nexgen.criteria.PaginationCriteria;
import com.enzith.nexgen.dto.request.MemberMembershipRequest;
import com.enzith.nexgen.dto.response.InstallmentResponse;
import com.enzith.nexgen.dto.response.MemberMembershipResponse;
import com.enzith.nexgen.entity.Installment;
import com.enzith.nexgen.entity.Member;
import com.enzith.nexgen.entity.MemberMembership;
import com.enzith.nexgen.entity.MembershipType;
import com.enzith.nexgen.entity.Notification;
import com.enzith.nexgen.enums.MembershipStatus;
import com.enzith.nexgen.enums.NotificationStatus;
import com.enzith.nexgen.enums.NotificationType;
import com.enzith.nexgen.enums.PaymentStatus;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.enums.Status;
import com.enzith.nexgen.exception.MemberException;
import com.enzith.nexgen.repository.InstallmentRepository;
import com.enzith.nexgen.repository.MemberMembershipRepository;
import com.enzith.nexgen.repository.MemberRepository;
import com.enzith.nexgen.repository.MembershipTypeRepository;
import com.enzith.nexgen.repository.NotificationRepository;
import com.enzith.nexgen.service.MemberMembershipService;
import com.enzith.nexgen.utility.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.enzith.nexgen.common.AppConstants.MEMBER_EXPIRATION_NOTIFICATION_MESSAGE;
import static com.enzith.nexgen.common.AppConstants.MEMBER_PENDING_EXPIRATION_NOTIFICATION_MESSAGE;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberMembershipServiceImpl implements MemberMembershipService {

    private final MemberMembershipRepository memberMembershipRepository;
    private final MembershipTypeRepository membershipTypeRepository;
    private final MemberRepository memberRepository;
    private final InstallmentRepository installmentRepository;
    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public String createMemberMembership(MemberMembershipRequest memberMembershipRequest) {
        MembershipType membershipType = validateMembershipType(memberMembershipRequest.getMembershipTypeId());

        Member existingMember = memberRepository.findById(memberMembershipRequest.getMemberId())
                .orElseThrow(() -> new MemberException(ResponseCode.MEMBER_NOT_FOUND));

        MemberMembership memberMembership = getMemberMembership(existingMember, memberMembershipRequest, membershipType);
        memberMembershipRepository.save(memberMembership);

        if (shouldCreateInstallments(memberMembershipRequest, membershipType)) {
            List<Installment> installments = generateInstallments(existingMember, memberMembership);
            installmentRepository.saveAll(installments);
        }
        return existingMember.getMembershipNo();
    }

    @Override
    @Transactional
    public String updateMemberMembership(MemberMembershipRequest memberMembershipRequest) {

        Optional<MemberMembership> activeMembershipOpt = memberMembershipRepository
                .findByMemberMembershipIdAndStatus(memberMembershipRequest.getMemberMembershipId(), Status.ACTIVE);

        Member existingMember = memberRepository.findById(memberMembershipRequest.getMemberId())
                .orElseThrow(() -> new MemberException(ResponseCode.MEMBER_NOT_FOUND));

        activeMembershipOpt.ifPresent(memberMembership -> handleMembershipChange(existingMember, memberMembershipRequest, memberMembership));
        return existingMember.getMembershipNo();
    }

    @Override
    public MemberMembershipResponse renewMembership(MemberMembershipRequest memberMembershipRequest) {
        Member existingMember = memberRepository.findById(memberMembershipRequest.getMemberId())
                .orElseThrow(() -> new MemberException(ResponseCode.MEMBER_NOT_FOUND));
        MembershipType newMembershipType = validateMembershipType(memberMembershipRequest.getMembershipTypeId());
        MemberMembership memberMembership = renewMembershipAndInstallments(existingMember, memberMembershipRequest, newMembershipType);
        return modelMapper.map(memberMembership, MemberMembershipResponse.class);
    }

    @Override
    public MemberMembershipResponse findMembershipById(Long membershipId) {
        return modelMapper.map(memberMembershipRepository.findById(membershipId), MemberMembershipResponse.class);
    }

    @Override
    public MemberMembershipResponse findMembershipForRenewal(Long membershipId) {
        return memberMembershipRepository.findById(membershipId)
                .map(this::mapToMembershipResponseForRenewal)
                .orElseThrow(() -> new IllegalArgumentException("Membership with ID " + membershipId + " not found"));
    }

    private MemberMembershipResponse mapToMembershipResponseForRenewal(MemberMembership membership) {
        MemberMembershipResponse response = modelMapper.map(membership, MemberMembershipResponse.class);

        if (!MembershipStatus.EXPIRED.name().equals(response.getStatus())) {
            LocalDate startDate = response.getEndDate().plusDays(1);
            LocalDate endDate = startDate.plusDays(response.getMembershipType().getDurationInDays());
            response.setStartDate(startDate);
            response.setEndDate(endDate);
        }

        return response;
    }

    @Override
    public Map<String, Object> findAllMemberships(String firstName, String phoneNo, Integer currentPage, Integer pageSize) {
        PaginationCriteria paginationCriteria = PaginationCriteria.builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();

        Pageable pageable = PaginationUtils.getPage(paginationCriteria);

        Page<MemberMembership> memberships = memberRepository
                .findByFirstNameContainingIgnoreCaseOrPhoneNoContaining(firstName, phoneNo)
                .map(member -> memberMembershipRepository.findByMemberAndStatusNot(member, MembershipStatus.INACTIVE, pageable))
                .orElse(memberMembershipRepository.findAll(pageable));

        return PaginationUtils.convertToPagination(memberships.map(membership ->
                modelMapper.map(membership, MemberMembershipResponse.class)));
    }

    @Override
    public void expireMembership() {
        List<MemberMembership> expiredMemberships = updateMembershipStatus(LocalDate.now());
        List<MemberMembership> membershipsToExpireSoon = updateMembershipStatus(LocalDate.now().plusDays(5));

        List<Notification> expiredNotifications = expiredMemberships.stream()
                .map(memberMembership -> getNotificationForExpiredMembership(memberMembership, MEMBER_EXPIRATION_NOTIFICATION_MESSAGE))
                .toList();

        List<Notification> expiringSoonNotifications = membershipsToExpireSoon.stream()
                .map(memberMembership -> getNotificationForExpiredMembership(memberMembership, MEMBER_PENDING_EXPIRATION_NOTIFICATION_MESSAGE))
                .toList();

        List<Notification> allNotifications = Stream.concat(expiredNotifications.stream(), expiringSoonNotifications.stream()).toList();
        notificationRepository.saveAll(allNotifications);
    }

    private List<MemberMembership> updateMembershipStatus(LocalDate targetDate) {
        List<MemberMembership> memberships = memberMembershipRepository.findMembershipsToExpire(targetDate, MembershipStatus.ACTIVE);

        if (!memberships.isEmpty()) {
            memberships.forEach(memberMembership -> memberMembership.setStatus(MembershipStatus.EXPIRED));
            memberMembershipRepository.saveAll(memberships);
        }
        return memberships;
    }


    private MembershipType validateMembershipType(Long membershipTypeId) {
        return membershipTypeRepository.findById(membershipTypeId)
                .orElseThrow(() -> new MemberException(ResponseCode.MEMBERSHIP_TYPE_NOT_FOUND));
    }

    private MemberMembership getMemberMembership(Member existingMember, MemberMembershipRequest memberMembershipRequest, MembershipType membershipType) {
        return MemberMembership.builder()
                .membershipType(membershipType)
                .member(existingMember)
                .startDate(existingMember.getJoinDate())
                .endDate(existingMember.getJoinDate().plusDays(membershipType.getDurationInDays()))
                .totalAmount(membershipType.getMembershipFee())
                .discount(memberMembershipRequest.getDiscount())
                .netAmount(membershipType.getMembershipFee() - memberMembershipRequest.getDiscount())
                .paymentStatus(PaymentStatus.UNPAID)
                .status(MembershipStatus.ACTIVE)
                .isPaidInInstallments(
                        membershipType.getIsInstallmentsAllowed() ? memberMembershipRequest.getIsPaidInInstallments() : false)
                .installmentCount(memberMembershipRequest.getInstallmentCount())
                .build();
    }

    private MemberMembership getRenewalMembership(Member existingMember, MemberMembershipRequest memberMembershipRequest, MembershipType membershipType) {
        MemberMembership memberMembership = getMemberMembership(existingMember, memberMembershipRequest, membershipType);
        memberMembership.setStartDate(memberMembershipRequest.getStartDate());
        memberMembership.setEndDate(memberMembershipRequest.getEndDate());
        return memberMembership;
    }

    private boolean shouldCreateInstallments(MemberMembershipRequest memberMembershipRequest, MembershipType membershipType) {
        return memberMembershipRequest.getIsPaidInInstallments() != null
                && memberMembershipRequest.getIsPaidInInstallments()
                && membershipType.getIsInstallmentsAllowed();
    }

    private List<Installment> generateInstallments(Member member, MemberMembership memberMembership) {
        int installmentCount = memberMembership.getInstallmentCount();
        double installmentAmount = Math.round((memberMembership.getNetAmount() / installmentCount) * 100.0) / 100.0;

        List<Installment> installments = new ArrayList<>();
        for (int i = 0; i < installmentCount; i++) {
            installments.add(Installment.builder()
                    .member(member)
                    .memberMembership(memberMembership)
                    .installmentNo(i + 1)
                    .installmentAmount(installmentAmount)
                    .dueDate(member.getJoinDate().plusMonths(i))
                    .paymentStatus(PaymentStatus.UNPAID)
                    .status(Status.ACTIVE)
                    .build());
        }
        return installments;
    }

    private void handleMembershipChange(Member existingMember, MemberMembershipRequest memberMembershipRequest, MemberMembership activeMembership) {
        deactivateMembershipAndInstallments(activeMembership, existingMember);

        MembershipType newMembershipType = validateMembershipType(memberMembershipRequest.getMembershipTypeId());
        createAndSaveMembershipAndInstallments(existingMember, memberMembershipRequest, newMembershipType);

    }

    private void deactivateMembershipAndInstallments(MemberMembership activeMembership, Member member) {
        if (PaymentStatus.UNPAID.equals(activeMembership.getPaymentStatus())) {
            activeMembership.setStatus(MembershipStatus.INACTIVE);
            memberMembershipRepository.save(activeMembership);

            if (Boolean.TRUE.equals(activeMembership.getIsPaidInInstallments())) {
                List<Installment> installments = installmentRepository.findByMemberAndMemberMembership(member, activeMembership);

                if (installments.stream().anyMatch(installment -> PaymentStatus.PAID.equals(installment.getPaymentStatus()))) {
                    throw new MemberException(ResponseCode.MEMBER_ALREADY_MAKE_A_PAYMENT);
                }

                installments.forEach(installment -> installment.setStatus(Status.INACTIVE));
                installmentRepository.saveAll(installments);
            }
        } else {
            throw new MemberException(ResponseCode.MEMBER_ALREADY_MAKE_A_PAYMENT);
        }
    }

    private void createAndSaveMembershipAndInstallments(Member member, MemberMembershipRequest memberMembershipRequest, MembershipType membershipType) {
        MemberMembership memberMembership = getMemberMembership(member, memberMembershipRequest, membershipType);
        memberMembershipRepository.save(memberMembership);

        if (shouldCreateInstallments(memberMembershipRequest, membershipType)) {
            createInstallments(member, memberMembership);
        }
    }

    private MemberMembership renewMembershipAndInstallments(Member member, MemberMembershipRequest memberMembershipRequest, MembershipType membershipType) {
        MemberMembership memberMembership = getRenewalMembership(member, memberMembershipRequest, membershipType);
        memberMembershipRepository.save(memberMembership);

        if (shouldCreateInstallments(memberMembershipRequest, membershipType)) {
            createInstallments(member, memberMembership);
        }
        return memberMembership;
    }

    private void createInstallments(Member member, MemberMembership memberMembership) {
        List<Installment> installments = generateInstallments(member, memberMembership);
        installmentRepository.saveAll(installments);
    }

    private Notification getNotificationForExpiredMembership(MemberMembership membership, String message) {
        return Notification.builder()
                .type(NotificationType.MEMBER_EXPIRATION)
                .message(String.format(message,
                        membership.getMember().getFirstName()+" "+membership.getMember().getLastName(),
                        membership.getMember().getMembershipNo())
                )
                .status(NotificationStatus.NEW)
                .build();
    }
}
