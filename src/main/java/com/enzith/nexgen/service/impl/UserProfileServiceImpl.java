package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.criteria.PaginationCriteria;
import com.enzith.nexgen.criteria.UserCriteria;
import com.enzith.nexgen.dto.request.UserProfileRequest;
import com.enzith.nexgen.dto.response.UserProfileResponse;
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
    public UserProfileResponse createUserProfile(UserProfileRequest userProfileRequest) {
        userProfileRepository.findUserByEmail(userProfileRequest.getEmail())
                .ifPresent(user -> {
                    throw new UserProfileException(ResponseCode.USER_PROFILE_EMAIL_IS_ALREADY_EXIST);
                });
        UserProfile userProfile = modelMapper.map(userProfileRequest, UserProfile.class);
        userProfile.setPassword(passwordEncoder.encode(userProfileRequest.getPassword()));
        UserProfileResponse userProfileResponse = modelMapper.map(userProfileRepository.save(userProfile), UserProfileResponse.class);
        userProfileResponse.setPassword(null);
        return userProfileResponse;
    }

    @Override
    public UserProfileResponse updateUserProfile(UserProfileRequest userProfileRequest) {
        UserProfile userProfile = userProfileRepository.findById(userProfileRequest.getUserProfileId())
                .orElseThrow(() -> new UserProfileException(ResponseCode.USER_PROFILE_NOT_FOUND));

        userProfile.setFirstName(userProfileRequest.getFirstName());
        userProfile.setLastName(userProfileRequest.getLastName());
        userProfile.setAddress(userProfileRequest.getAddress());
        userProfile.setPassword(userProfileRequest.getPassword());
        userProfile.setNic(userProfileRequest.getNic());
        userProfile.setContactNo(userProfileRequest.getContactNo());
        userProfile.setIsActive(userProfileRequest.getIsActive());
        userProfile.setUserRole(UserRole.builder().userRoleId(userProfileRequest.getUserRoleId()).build());

        UserProfile updatedUserProfile = userProfileRepository.save(userProfile);
        UserProfileResponse updatedDTO = modelMapper.map(updatedUserProfile, UserProfileResponse.class);
        updatedDTO.setPassword(null);
        return updatedDTO;
    }

    @Override
    public UserProfileResponse findUserProfileById(Long userProfileId) {
        return modelMapper.map(userProfileRepository.findById(userProfileId), UserProfileResponse.class);
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
        return PaginationUtils.convertToPagination(userProfiles.map(userProfile -> modelMapper.map(userProfile, UserProfileRequest.class)));
    }
}
