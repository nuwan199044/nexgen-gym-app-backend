package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.request.MemberPaymentRequest;
import com.enzith.nexgen.dto.response.InstallmentResponse;
import com.enzith.nexgen.dto.response.MemberPaymentResponse;

import java.util.List;
import java.util.Map;

public interface MemberPaymentService {
    MemberPaymentResponse createMembershipPayment(MemberPaymentRequest memberPaymentRequest);
    MemberPaymentResponse createMembershipInstallmentPayment(MemberPaymentRequest memberPaymentRequest);
    MemberPaymentResponse createPersonalTrainingPayment(MemberPaymentRequest memberPaymentRequest);
    Map<String, Object> findAllMemberPayments(String firstName, String phoneNo, Integer currentPage, Integer pageSize);
    List<InstallmentResponse> findAllMembershipPaymentInstallments(Long memberMembershipId);
}
