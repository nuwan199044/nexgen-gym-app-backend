package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.request.MemberMembershipRequest;

public interface MemberMembershipService {
    String createMemberMembership(MemberMembershipRequest memberMembershipRequest);
    String updateMemberMembership(MemberMembershipRequest memberMembershipRequest);

}
