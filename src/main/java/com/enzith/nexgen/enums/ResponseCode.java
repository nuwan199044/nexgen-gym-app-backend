package com.enzith.nexgen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    USER_NOT_FOUND(500, "User not found"),
    INVALID_CREDENTIALS(400, "Invalid credentials"),
    USER_PROFILE_EMAIL_IS_ALREADY_EXIST(400, "User profile email is already exist"),
    MEMBER_EMAIL_IS_ALREADY_EXIST(400, "Member email is already exist"),
    TRAINER_PHONE_NO_1_IS_ALREADY_EXIST(400, "Trainer phone no 1 is already exist"),
    MEMBER_ALREADY_MAKE_A_PAYMENT(400, "Member already make a payment. cannot update membership type"),
    MEMBER_ALREADY_PAID_AN_INSTALLMENT(400, "Member already paid an instalment. cannot update installment count"),
    MEMBERSHIP_TYPE_NAME_IS_ALREADY_EXIST(400, "Membership type name is already exist"),
    USER_PROFILE_NOT_FOUND(400, "User profile not found"),
    MEMBERSHIP_TYPE_NOT_FOUND(400, "Membership type not found"),
    MEMBERSHIP_NOT_FOUND(400, "Membership not found"),
    MEMBER_NOT_FOUND(400, "Member not found"),
    TRAINER_NOT_FOUND(400, "Trainer not found"),
    USER_ROLE_NOT_FOUND(400, "User role not found"),
    USER_PROFILE_CREATED_SUCCESS(400, "User profile is created successfully"),
    USER_PROFILE_UPDATED_SUCCESS(400, "User profile is updated successfully"),
    USER_PROFILE_FIND_SUCCESS(400, "User profile is fetched successfully"),
    TRAINER_FIND_SUCCESS(400, "Trainer is fetched successfully"),
    MEMBERSHIP_TYPE_CREATED_SUCCESS(400, "Membership type is created successfully"),
    MEMBER_CREATED_SUCCESS(400, "Member is created successfully"),
    TRAINER_CREATED_SUCCESS(400, "Trainer is created successfully"),
    MEMBERSHIP_CREATED_SUCCESS(400, "Membership is created successfully"),
    MEMBER_UPDATED_SUCCESS(400, "Member is updated successfully"),
    TRAINER_UPDATED_SUCCESS(400, "Trainer is updated successfully"),
    MEMBERSHIP_UPDATED_SUCCESS(400, "Membership is updated successfully"),
    MEMBERSHIP_TYPE_UPDATED_SUCCESS(400, "Membership type is updated successfully"),
    MEMBERSHIP_TYPE_FIND_SUCCESS(400, "Membership type is fetched successfully"),
    LOGIN_IS_SUCCESS(400, "login is success"),
    METHOD_ARGUMENT_NOT_VALID(400, "Method argument is not valid"),
    HTTP_MESSAGE_NOT_READABLE(400, "HTTP message not readable"),
    UNHANDLED_EXCEPTION(500, "Unhandled exception");

    private final int code;
    private final String message;
}