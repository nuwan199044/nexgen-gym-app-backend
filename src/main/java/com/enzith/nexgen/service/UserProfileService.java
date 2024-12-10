package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.request.UserProfileRequest;
import com.enzith.nexgen.dto.response.UserProfileResponse;

import java.util.Map;

public interface UserProfileService {
    UserProfileResponse createUserProfile(UserProfileRequest userProfileRequest);
    UserProfileResponse updateUserProfile(UserProfileRequest userProfileRequest);
    UserProfileResponse findUserProfileById(Long userProfileId);
    Map<String, Object> findAllUserProfiles(String firstName, String email, Integer currentPage, Integer pageSize);
}
