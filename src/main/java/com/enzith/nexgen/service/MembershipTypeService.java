package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.MembershipTypeDTO;

import java.util.Map;

public interface MembershipTypeService {
    MembershipTypeDTO createMembershipType(MembershipTypeDTO membershipTypeDTO);
    MembershipTypeDTO updateMembershipType(MembershipTypeDTO membershipTypeDTO);
    MembershipTypeDTO findMembershipTypeById(Long membershipTypeId);
    Map<String, Object> findAllMembershipTypes(Integer currentPage, Integer pageSize);
}
