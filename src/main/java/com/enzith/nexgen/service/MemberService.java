package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.request.MemberRequest;

import java.util.Map;

public interface MemberService {
    String createMember(MemberRequest memberRequest);
    String updateMember(MemberRequest memberRequest);

    Map<String, Object> findAllMembers(String firstName, String phoneNo, Integer currentPage, Integer pageSize);
}
