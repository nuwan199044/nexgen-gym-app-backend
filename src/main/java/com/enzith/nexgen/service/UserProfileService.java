package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.UserProfileDTO;

import java.util.Map;

public interface UserProfileService {
    UserProfileDTO createUserProfile(UserProfileDTO userProfileDTO);
    UserProfileDTO updateUserProfile(UserProfileDTO userProfileDTO);
    UserProfileDTO findUserProfileById(Long userProfileId);
    Map<String, Object> findAllUserProfiles(String firstName, String email, Integer currentPage, Integer pageSize);
}
