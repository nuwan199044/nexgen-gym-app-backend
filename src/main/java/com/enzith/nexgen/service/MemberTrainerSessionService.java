package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.request.MemberTrainerSessionRequest;
import com.enzith.nexgen.dto.response.MemberTrainerSessionResponse;

import java.util.Map;

public interface MemberTrainerSessionService {

    MemberTrainerSessionResponse createMemberTrainerSession(MemberTrainerSessionRequest memberTrainerSessionRequest);
    MemberTrainerSessionResponse updateMemberTrainerSession(MemberTrainerSessionRequest memberTrainerSessionRequest);
    MemberTrainerSessionResponse findMemberTrainerSessionById(Long memberTrainerSessionId);
    Map<String, Object> findAllMemberTrainerSessions(Long trainerId, Long memberId, Integer currentPage, Integer pageSize);
}
