package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.APIResponse;
import com.enzith.nexgen.dto.UserProfileDTO;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.UserProfileService;
import com.enzith.nexgen.utility.APIResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<APIResponse<UserProfileDTO>> createUserProfile(@RequestBody UserProfileDTO userProfileDTO) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.USER_PROFILE_CREATED_SUCCESS,
                        userProfileService.createUserProfile(userProfileDTO)
                ), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<UserProfileDTO>> updateUserProfile(@RequestBody UserProfileDTO userProfileDTO) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.USER_PROFILE_UPDATED_SUCCESS,
                        userProfileService.updateUserProfile(userProfileDTO)
                ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllUserProfiles(
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "current_page") Integer currentPage,
            @RequestParam(value = "page_size") Integer pageSize) {
        return new ResponseEntity<>(userProfileService.findAllUserProfiles(firstName, email, currentPage, pageSize), HttpStatus.OK);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<APIResponse<UserProfileDTO>> findUserProfileById(@RequestParam(value = "user_profile_id") Long userProfileId) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.USER_PROFILE_FIND_SUCCESS,
                        userProfileService.findUserProfileById(userProfileId)
                ), HttpStatus.OK);
    }

}
