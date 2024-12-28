package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.request.MemberPaymentRequest;
import com.enzith.nexgen.dto.response.APIResponse;
import com.enzith.nexgen.dto.response.InstallmentResponse;
import com.enzith.nexgen.dto.response.MemberPaymentResponse;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.MemberPaymentService;
import com.enzith.nexgen.utility.APIResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/member-payment")
@RequiredArgsConstructor
@Slf4j
public class MemberPaymentController {

    private final MemberPaymentService memberPaymentService;

    @PostMapping
    public ResponseEntity<APIResponse<MemberPaymentResponse>> createMembershipPayment(@RequestBody MemberPaymentRequest memberPaymentRequest) {
        log.info("Received request to create membership payment for member ID: {}", memberPaymentRequest.getMemberId());
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBERSHIP_PAYMENT_CREATED_SUCCESS,
                        memberPaymentService.createMembershipPayment(memberPaymentRequest)
                ), HttpStatus.CREATED);
    }

    @PostMapping("/installment")
    public ResponseEntity<APIResponse<MemberPaymentResponse>> createMembershipInstallmentPayment(@RequestBody MemberPaymentRequest memberPaymentRequest) {
        log.info("Received request to create membership installment payment for member ID: {}", memberPaymentRequest.getMemberId());
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBERSHIP_INSTALLMENT_PAYMENT_CREATED_SUCCESS,
                        memberPaymentService.createMembershipInstallmentPayment(memberPaymentRequest)
                ), HttpStatus.CREATED);
    }

    @GetMapping("/installment")
    public ResponseEntity<List<InstallmentResponse>> findAllMembershipPaymentInstallments(
            @RequestParam(value = "member_membership_id") Long memberMembershipId) {
        log.info("Received request to fetch all installments for membership ID: {}", memberMembershipId);
        return new ResponseEntity<>(memberPaymentService.findAllMembershipPaymentInstallments(memberMembershipId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllMemberPayments(
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "phone_no", required = false) String phoneNo,
            @RequestParam(value = "current_page") Integer currentPage,
            @RequestParam(value = "page_size") Integer pageSize) {
        log.info("Received request to fetch all member payments with filters - First Name: {}, Phone No: {}, Current Page: {}, Page Size: {}",
                firstName, phoneNo, currentPage, pageSize);
        return new ResponseEntity<>(memberPaymentService.findAllMemberPayments(firstName, phoneNo, currentPage, pageSize), HttpStatus.OK);
    }

}
