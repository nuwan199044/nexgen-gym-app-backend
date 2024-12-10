package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.response.APIResponse;
import com.enzith.nexgen.dto.request.MemberRequest;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.MemberService;
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
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<APIResponse<String>> createMember(@RequestBody MemberRequest memberRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBER_CREATED_SUCCESS,
                        memberService.createMember(memberRequest)
                ), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateMember(@RequestBody MemberRequest memberRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBER_UPDATED_SUCCESS,
                        memberService.updateMember(memberRequest)
                ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllMembers(
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "phone_no", required = false) String phoneNo,
            @RequestParam(value = "current_page") Integer currentPage,
            @RequestParam(value = "page_size") Integer pageSize) {
        return new ResponseEntity<>(memberService.findAllMembers(firstName, phoneNo, currentPage, pageSize), HttpStatus.OK);
    }

}
