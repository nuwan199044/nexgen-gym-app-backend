package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.criteria.MemberPaymentCriteria;
import com.enzith.nexgen.criteria.PaginationCriteria;
import com.enzith.nexgen.dto.request.MemberPaymentRequest;
import com.enzith.nexgen.dto.response.InstallmentResponse;
import com.enzith.nexgen.dto.response.MemberPaymentResponse;
import com.enzith.nexgen.entity.Installment;
import com.enzith.nexgen.entity.Member;
import com.enzith.nexgen.entity.MemberMembership;
import com.enzith.nexgen.entity.MemberPayment;
import com.enzith.nexgen.enums.MembershipStatus;
import com.enzith.nexgen.enums.PaymentMode;
import com.enzith.nexgen.enums.PaymentStatus;
import com.enzith.nexgen.enums.PaymentType;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.exception.MemberException;
import com.enzith.nexgen.exception.MemberMembershipException;
import com.enzith.nexgen.repository.InstallmentRepository;
import com.enzith.nexgen.repository.MemberMembershipRepository;
import com.enzith.nexgen.repository.MemberPaymentRepository;
import com.enzith.nexgen.repository.MemberRepository;
import com.enzith.nexgen.service.MemberPaymentService;
import com.enzith.nexgen.specification.MemberPaymentSpecification;
import com.enzith.nexgen.utility.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberPaymentServiceImpl implements MemberPaymentService {

    private final MemberMembershipRepository memberMembershipRepository;
    private final MemberPaymentRepository memberPaymentRepository;
    private final MemberRepository memberRepository;
    private final InstallmentRepository installmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public MemberPaymentResponse createMembershipPayment(MemberPaymentRequest memberPaymentRequest) {
        log.info("Creating membership payment for member ID: {}", memberPaymentRequest.getMemberId());
        Member member = validateMember(memberPaymentRequest);
        MemberMembership memberMembership = validateMemberMembership(memberPaymentRequest, member);

        log.info("Saving payment for member membership ID: {}", memberPaymentRequest.getMemberMembershipId());
        MemberPayment memberPayment = memberPaymentRepository.save(mapMemberMembership(memberMembership));

        memberMembership.setPaymentStatus(PaymentStatus.PAID);
        log.info("Updating payment status for membership ID: {}", memberPaymentRequest.getMemberMembershipId());
        memberMembershipRepository.save(memberMembership);

        log.info("Payment created successfully for member ID: {}", memberPaymentRequest.getMemberId());
        return modelMapper.map(memberPayment, MemberPaymentResponse.class);
    }

    @Override
    public MemberPaymentResponse createMembershipInstallmentPayment(MemberPaymentRequest memberPaymentRequest) {
        log.info("Creating membership installment payment for member ID: {}", memberPaymentRequest.getMemberId());
        Member member = validateMember(memberPaymentRequest);
        MemberMembership memberMembership = validateMemberMembership(memberPaymentRequest, member);
        Installment installment = validateInstallment(memberPaymentRequest);
        validateInstallmentPaymentStatus(installment);
        installment.setPaymentStatus(PaymentStatus.PAID);
        log.info("Saving installment payment for installment ID: {}", memberPaymentRequest.getInstallmentId());
        installmentRepository.save(installment);

        MemberPayment memberPayment = memberPaymentRepository.save(mapInstallment(memberMembership, installment));

        if (areAllInstallmentsPaid(memberMembership)) {
            log.info("All installments paid, updating membership payment status for membership ID: {}", memberMembership.getMemberMembershipId());
            markMembershipAsPaid(memberMembership);
        }

        log.info("Installment payment created successfully for member ID: {}", memberPaymentRequest.getMemberId());
        return modelMapper.map(memberPayment, MemberPaymentResponse.class);
    }

    @Override
    public MemberPaymentResponse createPersonalTrainingPayment(MemberPaymentRequest memberPaymentRequest) {
        return null;
    }

    @Override
    public Map<String, Object> findAllMemberPayments(String firstName, String phoneNo, Integer currentPage, Integer pageSize) {
        log.info("Fetching all member payments with filters - First Name: {}, Phone No: {}, Current Page: {}, Page Size: {}",
                firstName, phoneNo, currentPage, pageSize);

        MemberPaymentCriteria criteria = MemberPaymentCriteria.builder()
                .firstName(firstName)
                .phoneNo(phoneNo)
                .build();

        PaginationCriteria paginationCriteria = PaginationCriteria.builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();

        Pageable pageable = PaginationUtils.getPage(paginationCriteria);
        Page<MemberPayment> memberPayments = memberPaymentRepository.findAll(new MemberPaymentSpecification(criteria), pageable);

        log.info("Fetched {} member payments for the given filters.", memberPayments.getTotalElements());
        return PaginationUtils.convertToPagination(memberPayments.map(memberPayment -> modelMapper.map(memberPayment, MemberPaymentResponse.class)));
    }

    @Override
    public List<InstallmentResponse> findAllMembershipPaymentInstallments(Long memberMembershipId) {
        MemberMembership memberMembership = validateMembership(memberMembershipId);
        return installmentRepository.findByMemberMembership(memberMembership)
                .stream()
                .map(installment -> modelMapper.map(installment, InstallmentResponse.class))
                .toList();
    }

    private boolean areAllInstallmentsPaid(MemberMembership memberMembership) {
        return installmentRepository.findByMemberMembership(memberMembership)
                .stream()
                .allMatch(installment -> installment.getPaymentStatus().equals(PaymentStatus.PAID));
    }

    private void markMembershipAsPaid(MemberMembership memberMembership) {
        log.info("Marking membership ID: {} as paid", memberMembership.getMemberMembershipId());
        memberMembership.setPaymentStatus(PaymentStatus.PAID);
        memberMembershipRepository.save(memberMembership);
    }

    private MemberPayment mapMemberMembership(MemberMembership memberMembership) {
        return MemberPayment.builder()
                .member(memberMembership.getMember())
                .memberMembership(memberMembership)
                .paymentDate(LocalDateTime.now())
                .paymentAmount(memberMembership.getNetAmount())
                .paymentMode(PaymentMode.ONE_TIME)
                .paymentType(PaymentType.MEMBERSHIP)
                .build();
    }

    private MemberPayment mapInstallment(MemberMembership memberMembership, Installment installment) {
        return MemberPayment.builder()
                .member(memberMembership.getMember())
                .memberMembership(memberMembership)
                .paymentDate(LocalDateTime.now())
                .paymentAmount(installment.getInstallmentAmount())
                .paymentMode(PaymentMode.INSTALLMENT)
                .paymentType(PaymentType.MEMBERSHIP)
                .build();
    }

    private Member validateMember(MemberPaymentRequest memberPaymentRequest) {
        log.info("Validating member with ID: {}", memberPaymentRequest.getMemberId());
        return memberRepository.findById(memberPaymentRequest.getMemberId())
                .orElseThrow(() -> new MemberException(ResponseCode.MEMBER_NOT_FOUND));
    }

    private Installment validateInstallment(MemberPaymentRequest memberPaymentRequest) {
        log.info("Validating installment with ID: {}", memberPaymentRequest.getInstallmentId());
        return installmentRepository.findById(memberPaymentRequest.getInstallmentId())
                .orElseThrow(() -> new MemberException(ResponseCode.INSTALLMENT_NOT_FOUND));
    }

    private void validateInstallmentPaymentStatus(Installment installment) {
        log.info("Validating installment status: {}", installment.getPaymentStatus());
        if (installment.getPaymentStatus().equals(PaymentStatus.PAID)) {
            throw new MemberException(ResponseCode.INSTALLMENT_ALREADY_PAID);
        }
    }

    private MemberMembership validateMemberMembership(MemberPaymentRequest memberPaymentRequest, Member member) {
        log.info("Validating member membership with ID: {} for member ID: {}", memberPaymentRequest.getMemberMembershipId(), member.getMemberId());
        return memberMembershipRepository
                .findByMemberMembershipIdAndMemberAndStatusAndPaymentStatus(
                        memberPaymentRequest.getMemberMembershipId(),
                        member,
                        MembershipStatus.ACTIVE,
                        PaymentStatus.UNPAID)
                .orElseThrow(() -> new MemberMembershipException(ResponseCode.MEMBER_MEMBERSHIP_NOT_FOUND));
    }

    private MemberMembership validateMembership(Long membershipId) {
        log.info("Validating membership with ID: {}", membershipId);
        return memberMembershipRepository.findById(membershipId)
                .orElseThrow(() -> new MemberException(ResponseCode.MEMBERSHIP_NOT_FOUND));
    }
}
