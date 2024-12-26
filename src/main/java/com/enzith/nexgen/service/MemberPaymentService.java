package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.request.MemberPaymentRequest;
import com.enzith.nexgen.dto.response.MemberPaymentResponse;

public interface MemberPaymentService {
    MemberPaymentResponse createMembershipPayment(MemberPaymentRequest memberPaymentRequest);
    MemberPaymentResponse createMembershipInstallmentPayment(MemberPaymentRequest memberPaymentRequest);
    MemberPaymentResponse createPersonalTrainingPayment(MemberPaymentRequest memberPaymentRequest);
}
