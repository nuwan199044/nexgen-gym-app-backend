package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.request.MemberTrainerSessionRequest;
import com.enzith.nexgen.dto.response.APIResponse;
import com.enzith.nexgen.dto.response.MemberTrainerSessionResponse;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.MemberTrainerSessionService;
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
@RequestMapping("/api/member-training-session")
@RequiredArgsConstructor
public class MemberTrainerSessionController {

    private final MemberTrainerSessionService memberTrainerSessionService;

    @PostMapping
    public ResponseEntity<APIResponse<MemberTrainerSessionResponse>> createMemberTrainerSession(@RequestBody MemberTrainerSessionRequest memberTrainerSessionRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBER_TRAINING_SESSION_CREATED_SUCCESS,
                        memberTrainerSessionService.createMemberTrainerSession(memberTrainerSessionRequest)
                ), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<MemberTrainerSessionResponse>> updateMemberTrainerSession(@RequestBody MemberTrainerSessionRequest memberTrainerSessionRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBER_TRAINING_SESSION_UPDATED_SUCCESS,
                        memberTrainerSessionService.updateMemberTrainerSession(memberTrainerSessionRequest)
                ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllMemberTrainerSessions(
            @RequestParam(value = "trainer_id") Long trainerId,
            @RequestParam(value = "member_id") Long memberId,
            @RequestParam(value = "current_page") Integer currentPage,
            @RequestParam(value = "page_size") Integer pageSize) {
        return new ResponseEntity<>(memberTrainerSessionService.findAllMemberTrainerSessions(trainerId, memberId, currentPage, pageSize), HttpStatus.OK);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<APIResponse<MemberTrainerSessionResponse>> findMemberTrainerSessionById(@RequestParam(value = "member_trainer_session_id") Long memberTrainerSessionId) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.MEMBER_TRAINING_SESSION_FIND_SUCCESS,
                        memberTrainerSessionService.findMemberTrainerSessionById(memberTrainerSessionId)
                ), HttpStatus.OK);
    }
}
