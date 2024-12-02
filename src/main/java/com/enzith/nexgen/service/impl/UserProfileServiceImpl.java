package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.criteria.PaginationCriteria;
import com.enzith.nexgen.criteria.UserCriteria;
import com.enzith.nexgen.dto.UserProfileDTO;
import com.enzith.nexgen.entity.UserProfile;
import com.enzith.nexgen.entity.UserRole;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.exception.UserProfileException;
import com.enzith.nexgen.repository.UserProfileRepository;
import com.enzith.nexgen.service.UserProfileService;
import com.enzith.nexgen.specification.UserSpecification;
import com.enzith.nexgen.utility.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfileDTO createUserProfile(UserProfileDTO userProfileDTO) {
        userProfileRepository.findUserByEmail(userProfileDTO.getEmail())
                .ifPresent(user -> {
                    throw new UserProfileException(ResponseCode.USER_PROFILE_EMAIL_IS_ALREADY_EXIST);
                });
        UserProfile userProfile = modelMapper.map(userProfileDTO, UserProfile.class);
        userProfile.setPassword(passwordEncoder.encode(userProfileDTO.getPassword()));
        userProfileDTO = modelMapper.map(userProfileRepository.save(userProfile), UserProfileDTO.class);
        userProfileDTO.setPassword(null);
        return userProfileDTO;
    }

    @Override
    public UserProfileDTO updateUserProfile(UserProfileDTO userProfileDTO) {
        UserProfile userProfile = userProfileRepository.findUserByEmail(userProfileDTO.getEmail())
                .orElseThrow(() -> new UserProfileException(ResponseCode.USER_PROFILE_NOT_FOUND));

        userProfile.setFirstName(userProfileDTO.getFirstName());
        userProfile.setLastName(userProfileDTO.getLastName());
        userProfile.setAddress(userProfileDTO.getAddress());
        userProfile.setPassword(userProfileDTO.getPassword());
        userProfile.setNic(userProfileDTO.getNic());
        userProfile.setContactNo(userProfileDTO.getContactNo());
        userProfile.setIsActive(userProfileDTO.getIsActive());
        userProfile.setUserRole(UserRole.builder().userRoleId(userProfileDTO.getUserRoleId()).build());

        UserProfile updatedUserProfile = userProfileRepository.save(userProfile);
        UserProfileDTO updatedDTO = modelMapper.map(updatedUserProfile, UserProfileDTO.class);
        updatedDTO.setPassword(null);
        return updatedDTO;
    }

    @Override
    public UserProfileDTO findUserProfileById(Long userProfileId) {
        return modelMapper.map(userProfileRepository.findById(userProfileId), UserProfileDTO.class);
    }

    @Override
    public Map<String, Object> findAllUserProfiles(String firstName, String email, Integer currentPage, Integer pageSize) {
        UserCriteria criteria = UserCriteria.builder()
                .firstName(firstName)
                .email(email)
                .build();

        PaginationCriteria paginationCriteria = PaginationCriteria.builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();

        Pageable pageable = PaginationUtils.getPage(paginationCriteria);
        Page<UserProfile> userProfiles = userProfileRepository.findAll(new UserSpecification(criteria), pageable);
        return PaginationUtils.convertToPagination(userProfiles.map(userProfile -> modelMapper.map(userProfile, UserProfileDTO.class)));
    }
}
