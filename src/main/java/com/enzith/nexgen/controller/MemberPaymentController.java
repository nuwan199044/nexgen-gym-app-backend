package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.request.MemberPaymentRequest;
import com.enzith.nexgen.dto.response.APIResponse;
import com.enzith.nexgen.dto.response.MemberPaymentResponse;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.MemberPaymentService;
import com.enzith.nexgen.utility.APIResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member-payment")
@RequiredArgsConstructor
public class MemberPaymentController {

    private final MemberPaymentService memberPaymentService;

    @PostMapping
    public ResponseEntity<APIResponse<MemberPaymentResponse>> createMembershipPayment(@RequestBody MemberPaymentRequest memberPaymentRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBERSHIP_PAYMENT_CREATED_SUCCESS,
                        memberPaymentService.createMembershipPayment(memberPaymentRequest)
                ), HttpStatus.CREATED);
    }

    @PostMapping("/installment")
    public ResponseEntity<APIResponse<MemberPaymentResponse>> createMembershipInstallmentPayment(@RequestBody MemberPaymentRequest memberPaymentRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBERSHIP_INSTALLMENT_PAYMENT_CREATED_SUCCESS,
                        memberPaymentService.createMembershipInstallmentPayment(memberPaymentRequest)
                ), HttpStatus.CREATED);
    }

}
