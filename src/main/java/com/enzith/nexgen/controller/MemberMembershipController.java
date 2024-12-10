package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.response.APIResponse;
import com.enzith.nexgen.dto.request.MemberMembershipRequest;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.MemberMembershipService;
import com.enzith.nexgen.utility.APIResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
