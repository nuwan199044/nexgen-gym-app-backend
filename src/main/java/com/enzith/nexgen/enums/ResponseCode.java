package com.enzith.nexgen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    USER_NOT_FOUND(500, "User not found"),
    INVALID_CREDENTIALS(400, "Invalid credentials"),
    USER_PROFILE_EMAIL_IS_ALREADY_EXIST(400, "User profile email is already exist"),
    MEMBERSHIP_TYPE_NAME_IS_ALREADY_EXIST(400, "Membership type name is already exist"),
    USER_PROFILE_NOT_FOUND(400, "User profile not found"),
    MEMBERSHIP_TYPE_NOT_FOUND(400, "Membership type not found"),
    USER_ROLE_NOT_FOUND(400, "User role not found"),
    USER_PROFILE_CREATED_SUCCESS(400, "User profile is created successfully"),
    USER_PROFILE_UPDATED_SUCCESS(400, "User profile is updated successfully"),
    USER_PROFILE_FIND_SUCCESS(400, "User profile is fetched successfully"),
    MEMBERSHIP_TYPE_CREATED_SUCCESS(400, "Membership type is created successfully"),
    MEMBERSHIP_TYPE_UPDATED_SUCCESS(400, "Membership type is updated successfully"),
    MEMBERSHIP_TYPE_FIND_SUCCESS(400, "Membership type is fetched successfully"),
    LOGIN_IS_SUCCESS(400, "login is success");

    private final int code;
    private final String message;
}