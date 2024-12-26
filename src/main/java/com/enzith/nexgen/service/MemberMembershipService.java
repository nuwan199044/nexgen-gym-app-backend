package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.request.MemberMembershipRequest;

import java.util.Map;

public interface MemberMembershipService {
    String createMemberMembership(MemberMembershipRequest memberMembershipRequest);
    String updateMemberMembership(MemberMembershipRequest memberMembershipRequest);
    Map<String, Object> findAllMemberships(String firstName, String phoneNo, Integer currentPage, Integer pageSize);

}
