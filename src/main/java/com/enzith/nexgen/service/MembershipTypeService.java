package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.request.MembershipTypeRequest;
import com.enzith.nexgen.dto.response.MembershipTypeResponse;

import java.util.Map;

public interface MembershipTypeService {
    MembershipTypeResponse createMembershipType(MembershipTypeRequest membershipTypeResponse);
    MembershipTypeResponse updateMembershipType(MembershipTypeRequest membershipTypeResponse);
    MembershipTypeResponse findMembershipTypeById(Long membershipTypeId);
    Map<String, Object> findAllMembershipTypes(Integer currentPage, Integer pageSize);
}
