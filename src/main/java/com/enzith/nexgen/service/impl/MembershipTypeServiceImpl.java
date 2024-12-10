package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.criteria.PaginationCriteria;
import com.enzith.nexgen.dto.request.MembershipTypeRequest;
import com.enzith.nexgen.dto.response.MembershipTypeResponse;
import com.enzith.nexgen.entity.MembershipType;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.exception.MembershipTypeException;
import com.enzith.nexgen.repository.MembershipTypeRepository;
import com.enzith.nexgen.service.MembershipTypeService;
import com.enzith.nexgen.utility.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MembershipTypeServiceImpl implements MembershipTypeService {

    private final MembershipTypeRepository membershipTypeRepository;
    private final ModelMapper modelMapper;

    @Override
    public MembershipTypeResponse createMembershipType(MembershipTypeRequest membershipTypeRequest) {
        membershipTypeRepository.findMembershipTypeByMembershipTypeName(membershipTypeRequest.getMembershipTypeName())
                .ifPresent(user -> {
                    throw new MembershipTypeException(ResponseCode.MEMBERSHIP_TYPE_NAME_IS_ALREADY_EXIST);
                });
        MembershipType membershipType = modelMapper.map(membershipTypeRequest, MembershipType.class);
        return modelMapper.map(membershipTypeRepository.save(membershipType), MembershipTypeResponse.class);
    }

    @Override
    public MembershipTypeResponse updateMembershipType(MembershipTypeRequest membershipTypeRequest) {
        MembershipType membershipType = membershipTypeRepository.findById(membershipTypeRequest.getMembershipTypeId())
                .orElseThrow(() -> new MembershipTypeException(ResponseCode.MEMBERSHIP_TYPE_NOT_FOUND));

        membershipType.setMembershipFee(membershipTypeRequest.getMembershipFee());
        membershipType.setMembershipTypeName(membershipTypeRequest.getMembershipTypeName());
        membershipType.setDurationInDays(membershipTypeRequest.getDurationInDays());
        membershipType.setIsInstallmentsAllowed(membershipTypeRequest.getIsInstallmentsAllowed());

        MembershipType updatedMembershipType = membershipTypeRepository.save(membershipType);
        return modelMapper.map(updatedMembershipType, MembershipTypeResponse.class);
    }

    @Override
    public MembershipTypeResponse findMembershipTypeById(Long membershipTypeId) {
        return modelMapper.map(membershipTypeRepository.findById(membershipTypeId), MembershipTypeResponse.class);
    }

    @Override
    public Map<String, Object> findAllMembershipTypes(Integer currentPage, Integer pageSize) {
        PaginationCriteria paginationCriteria = PaginationCriteria.builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();

        Pageable pageable = PaginationUtils.getPage(paginationCriteria);
        Page<MembershipType> membershipTypes = membershipTypeRepository.findAll(pageable);
        return PaginationUtils.convertToPagination(membershipTypes.map(membershipType -> modelMapper.map(membershipType, MembershipTypeResponse.class)));
    }
}
