package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.criteria.PaginationCriteria;
import com.enzith.nexgen.dto.MembershipTypeDTO;
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
    public MembershipTypeDTO createMembershipType(MembershipTypeDTO membershipTypeDTO) {
        membershipTypeRepository.findMembershipTypeByMembershipTypeName(membershipTypeDTO.getMembershipTypeName())
                .ifPresent(user -> {
                    throw new MembershipTypeException(ResponseCode.MEMBERSHIP_TYPE_NAME_IS_ALREADY_EXIST);
                });
        MembershipType membershipType = modelMapper.map(membershipTypeDTO, MembershipType.class);
        return modelMapper.map(membershipTypeRepository.save(membershipType), MembershipTypeDTO.class);
    }

    @Override
    public MembershipTypeDTO updateMembershipType(MembershipTypeDTO membershipTypeDTO) {
        MembershipType membershipType = membershipTypeRepository.findById(membershipTypeDTO.getMembershipTypeId())
                .orElseThrow(() -> new MembershipTypeException(ResponseCode.MEMBERSHIP_TYPE_NOT_FOUND));

        membershipType.setMembershipFee(membershipTypeDTO.getMembershipFee());
        membershipType.setMembershipTypeName(membershipTypeDTO.getMembershipTypeName());
        membershipType.setDurationInDays(membershipTypeDTO.getDurationInDays());
        membershipType.setIsInstallmentsAllowed(membershipTypeDTO.getIsInstallmentsAllowed());

        MembershipType updatedMembershipType = membershipTypeRepository.save(membershipType);
        return modelMapper.map(updatedMembershipType, MembershipTypeDTO.class);
    }

    @Override
    public MembershipTypeDTO findMembershipTypeById(Long membershipTypeId) {
        return modelMapper.map(membershipTypeRepository.findById(membershipTypeId), MembershipTypeDTO.class);
    }

    @Override
    public Map<String, Object> findAllMembershipTypes(Integer currentPage, Integer pageSize) {
        PaginationCriteria paginationCriteria = PaginationCriteria.builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();

        Pageable pageable = PaginationUtils.getPage(paginationCriteria);
        Page<MembershipType> membershipTypes = membershipTypeRepository.findAll(pageable);
        return PaginationUtils.convertToPagination(membershipTypes.map(membershipType -> modelMapper.map(membershipType, MembershipTypeDTO.class)));
    }
}
