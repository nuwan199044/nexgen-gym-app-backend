package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.dto.request.MemberPaymentRequest;
import com.enzith.nexgen.dto.response.MemberPaymentResponse;
import com.enzith.nexgen.entity.Installment;
import com.enzith.nexgen.entity.Member;
import com.enzith.nexgen.entity.MemberMembership;
import com.enzith.nexgen.entity.MemberPayment;
import com.enzith.nexgen.enums.PaymentMode;
import com.enzith.nexgen.enums.PaymentStatus;
import com.enzith.nexgen.enums.PaymentType;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.enums.Status;
import com.enzith.nexgen.exception.MemberException;
import com.enzith.nexgen.exception.MemberMembershipException;
import com.enzith.nexgen.repository.InstallmentRepository;
import com.enzith.nexgen.repository.MemberMembershipRepository;
import com.enzith.nexgen.repository.MemberPaymentRepository;
import com.enzith.nexgen.repository.MemberRepository;
import com.enzith.nexgen.service.MemberPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        Member member = validateMember(memberPaymentRequest);
        MemberMembership memberMembership = validateMemberMembership(memberPaymentRequest, member);

        MemberPayment memberPayment = memberPaymentRepository.save(mapMemberMembership(memberMembership));
        memberMembership.setPaymentStatus(PaymentStatus.PAID);

        memberMembershipRepository.save(memberMembership);
        return modelMapper.map(memberPayment, MemberPaymentResponse.class);
    }

    @Override
    public MemberPaymentResponse createMembershipInstallmentPayment(MemberPaymentRequest memberPaymentRequest) {
        Member member = validateMember(memberPaymentRequest);
        MemberMembership memberMembership = validateMemberMembership(memberPaymentRequest, member);
        Installment installment = validateInstallment(memberPaymentRequest);
        installment.setPaymentStatus(PaymentStatus.PAID);
        MemberPayment memberPayment = memberPaymentRepository.save(mapInstallment(memberMembership, installment));
        memberMembership.setPaymentStatus(PaymentStatus.PAID);
        installmentRepository.save(installment);

//        installmentRepository.findByMemberAndMemberMembership()

        memberMembershipRepository.save(memberMembership);
        return modelMapper.map(memberPayment, MemberPaymentResponse.class);
    }

    @Override
    public MemberPaymentResponse createPersonalTrainingPayment(MemberPaymentRequest memberPaymentRequest) {
        return null;
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
        return memberRepository.findById(memberPaymentRequest.getMemberId())
                .orElseThrow(() -> new MemberException(ResponseCode.MEMBER_NOT_FOUND));
    }

    private Installment validateInstallment(MemberPaymentRequest memberPaymentRequest) {
        return installmentRepository.findById(memberPaymentRequest.getInstallmentId())
                .orElseThrow(() -> new MemberException(ResponseCode.INSTALLMENT_NOT_FOUND));
    }

    private MemberMembership validateMemberMembership(MemberPaymentRequest memberPaymentRequest, Member member) {
        return memberMembershipRepository
                .findByMemberMembershipIdAndMemberAndStatusAndPaymentStatus(
                        memberPaymentRequest.getMemberMembershipId(),
                        member,
                        Status.ACTIVE,
                        PaymentStatus.UNPAID)
                .orElseThrow(() -> new MemberMembershipException(ResponseCode.MEMBER_MEMBERSHIP_NOT_FOUND));
    }
}
