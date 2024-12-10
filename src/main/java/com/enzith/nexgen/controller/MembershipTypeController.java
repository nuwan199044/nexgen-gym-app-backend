package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.request.MembershipTypeRequest;
import com.enzith.nexgen.dto.response.APIResponse;
import com.enzith.nexgen.dto.response.MembershipTypeResponse;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.MembershipTypeService;
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

import java.util.Map;

@RestController
@RequestMapping("/api/membership-type")
@RequiredArgsConstructor
public class MembershipTypeController {

    private final MembershipTypeService membershipTypeService;

    @PostMapping
    public ResponseEntity<APIResponse<MembershipTypeResponse>> createMembershipType(@RequestBody MembershipTypeRequest membershipTypeRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBERSHIP_TYPE_CREATED_SUCCESS,
                        membershipTypeService.createMembershipType(membershipTypeRequest)
                ), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<MembershipTypeResponse>> updateMembershipType(@RequestBody MembershipTypeRequest membershipTypeRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBERSHIP_TYPE_UPDATED_SUCCESS,
                        membershipTypeService.updateMembershipType(membershipTypeRequest)
                ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllMembershipTypes(
            @RequestParam(value = "current_page") Integer currentPage,
            @RequestParam(value = "page_size") Integer pageSize) {
        return new ResponseEntity<>(membershipTypeService.findAllMembershipTypes(currentPage, pageSize), HttpStatus.OK);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<APIResponse<MembershipTypeResponse>> findMembershipTypeById(@RequestParam(value = "membership_type_id") Long membershipTypeId) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBERSHIP_TYPE_FIND_SUCCESS,
                        membershipTypeService.findMembershipTypeById(membershipTypeId)
                ), HttpStatus.OK);
    }

}
