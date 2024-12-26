package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.request.MemberMembershipRequest;
import com.enzith.nexgen.dto.response.InstallmentResponse;
import com.enzith.nexgen.dto.response.MemberMembershipResponse;

import java.util.List;
import java.util.Map;

public interface MemberMembershipService {
    String createMemberMembership(MemberMembershipRequest memberMembershipRequest);
    String updateMemberMembership(MemberMembershipRequest memberMembershipRequest);
    MemberMembershipResponse renewMembership(MemberMembershipRequest memberMembershipRequest);
    MemberMembershipResponse findMembershipById(Long membershipId);
    MemberMembershipResponse findMembershipForRenewal(Long membershipId);
    Map<String, Object> findAllMemberships(String firstName, String phoneNo, Integer currentPage, Integer pageSize);
    List<InstallmentResponse> findAllMembershipPaymentInstallments(Long memberMembershipId);

}
