package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.response.APIResponse;
import com.enzith.nexgen.dto.request.MemberMembershipRequest;
import com.enzith.nexgen.dto.response.InstallmentResponse;
import com.enzith.nexgen.dto.response.MemberMembershipResponse;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.MemberMembershipService;
import com.enzith.nexgen.utility.APIResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/member-membership")
@RequiredArgsConstructor
public class MemberMembershipController {

    private final MemberMembershipService memberMembershipService;

    @PostMapping
    public ResponseEntity<APIResponse<String>> createMemberMembership(@RequestBody MemberMembershipRequest membershipDTO) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBERSHIP_CREATED_SUCCESS,
                        memberMembershipService.createMemberMembership(membershipDTO)
                ), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateMemberMembership(@RequestBody MemberMembershipRequest membershipDTO) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBERSHIP_UPDATED_SUCCESS,
                        memberMembershipService.updateMemberMembership(membershipDTO)
                ), HttpStatus.OK);
    }

    @PutMapping("/renew")
    public ResponseEntity<APIResponse<MemberMembershipResponse>> renewMembership(@RequestBody MemberMembershipRequest membershipDTO) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBERSHIP_UPDATED_SUCCESS,
                        memberMembershipService.renewMembership(membershipDTO)
                ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllMemberships(
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "phone_no", required = false) String phoneNo,
            @RequestParam(value = "current_page") Integer currentPage,
            @RequestParam(value = "page_size") Integer pageSize) {
        return new ResponseEntity<>(memberMembershipService.findAllMemberships(firstName, phoneNo, currentPage, pageSize), HttpStatus.OK);
    }

    @GetMapping("/payment/installment")
    public ResponseEntity<List<InstallmentResponse>> findAllMembershipPaymentInstallments(
            @RequestParam(value = "member_membership_id") Long memberMembershipId) {
        return new ResponseEntity<>(memberMembershipService.findAllMembershipPaymentInstallments(memberMembershipId), HttpStatus.OK);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<APIResponse<MemberMembershipResponse>> findMembershipById(@RequestParam(value = "membership_id") Long membershipId) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBERSHIP_FIND_SUCCESS,
                        memberMembershipService.findMembershipById(membershipId)
                ), HttpStatus.OK);
    }

    @GetMapping("/find-for-renewal")
    public ResponseEntity<APIResponse<MemberMembershipResponse>> findMembershipForRenewal(@RequestParam(value = "membership_id") Long membershipId) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBERSHIP_FIND_SUCCESS,
                        memberMembershipService.findMembershipForRenewal(membershipId)
                ), HttpStatus.OK);
    }

}
