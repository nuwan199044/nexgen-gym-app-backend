package com.enzith.nexgen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    // User Errors
    USER_NOT_FOUND(500, "User not found"),
    INVALID_CREDENTIALS(400, "Invalid credentials"),
    USER_PROFILE_EMAIL_IS_ALREADY_EXIST(400, "User profile email is already exist"),
    USER_PROFILE_NOT_FOUND(400, "User profile not found"),
    USER_ROLE_NOT_FOUND(400, "User role not found"),

    // Member Errors
    MEMBER_EMAIL_IS_ALREADY_EXIST(400, "Member email is already exist"),
    MEMBER_ALREADY_MAKE_A_PAYMENT(400, "Member already make a payment. cannot update membership type"),
    MEMBER_ALREADY_COMPLETED_A_SESSION(400, "Member already completed a session. cannot update personal training"),
    MEMBER_ALREADY_MAKE_A_PAYMENT_FOR_TRAINING(400, "Member already make a payment for training. cannot update personal training info"),
    MEMBER_ALREADY_PAID_AN_INSTALLMENT(400, "Member already paid an instalment. cannot update installment count"),
    MEMBER_NOT_FOUND(400, "Member not found"),

    // Trainer Errors
    TRAINER_PHONE_NO_1_IS_ALREADY_EXIST(400, "Trainer phone no 1 is already exist"),
    TRAINER_NOT_FOUND(400, "Trainer not found"),

    // Membership Errors
    MEMBERSHIP_TYPE_NAME_IS_ALREADY_EXIST(400, "Membership type name is already exist"),
    MEMBERSHIP_TYPE_NOT_FOUND(400, "Membership type not found"),
    MEMBERSHIP_NOT_FOUND(400, "Membership not found"),

    // Member Membership Errors
    MEMBER_MEMBERSHIP_NOT_FOUND(400, "Member Membership not found"),

    // Session and Training Errors
    PERSONAL_TRAINING_NOT_FOUND(400, "Personal training not found"),
    MEMBER_TRAINING_SESSION_NOT_FOUND(400, "Member training session not found"),

    //Installment Errors
    INSTALLMENT_NOT_FOUND(400, "Installment not found"),
    INSTALLMENT_ALREADY_PAID(400, "Installment already paid"),

    // Package Errors
    TRAINER_PACKAGE_NOT_FOUND(400, "Trainer package not found"),

    // Validation Errors
    METHOD_ARGUMENT_NOT_VALID(400, "Method argument is not valid"),
    HTTP_MESSAGE_NOT_READABLE(400, "HTTP message not readable"),

    // System Errors
    UNHANDLED_EXCEPTION(500, "Unhandled exception"),

    // User Success
    USER_PROFILE_CREATED_SUCCESS(200, "User profile is created successfully"),
    USER_PROFILE_UPDATED_SUCCESS(200, "User profile is updated successfully"),
    USER_PROFILE_FIND_SUCCESS(200, "User profile is fetched successfully"),

    // Member Success
    MEMBER_CREATED_SUCCESS(200, "Member is created successfully"),
    MEMBER_UPDATED_SUCCESS(200, "Member is updated successfully"),
    MEMBER_FIND_SUCCESS(200, "Member is fetched successfully"),

    // Trainer Success
    TRAINER_FIND_SUCCESS(200, "Trainer is fetched successfully"),
    TRAINER_CREATED_SUCCESS(200, "Trainer is created successfully"),
    TRAINER_UPDATED_SUCCESS(200, "Trainer is updated successfully"),

    // Membership Success
    MEMBERSHIP_TYPE_CREATED_SUCCESS(200, "Membership type is created successfully"),
    MEMBERSHIP_CREATED_SUCCESS(200, "Membership is created successfully"),
    MEMBERSHIP_TYPE_UPDATED_SUCCESS(200, "Membership type is updated successfully"),
    MEMBERSHIP_UPDATED_SUCCESS(200, "Membership is updated successfully"),
    MEMBERSHIP_TYPE_FIND_SUCCESS(200, "Membership type is fetched successfully"),
    MEMBERSHIP_FIND_SUCCESS(200, "Membership is fetched successfully"),

    // Trainer Package Success
    TRAINER_PACKAGE_FIND_SUCCESS(200, "Trainer package is fetched successfully"),
    TRAINER_PACKAGE_CREATED_SUCCESS(200, "Trainer package is created successfully"),
    TRAINER_PACKAGE_UPDATED_SUCCESS(200, "Trainer package is updated successfully"),

    // Session and Training Success
    MEMBER_TRAINING_SESSION_FIND_SUCCESS(200, "Member training session is fetched successfully"),
    MEMBER_TRAINING_SESSION_CREATED_SUCCESS(200, "Member training session is created successfully"),
    MEMBER_TRAINING_SESSION_UPDATED_SUCCESS(200, "Member training session is updated successfully"),
    PERSONAL_TRAINING_SESSION_UPDATED_SUCCESS(200, "Personal training session is updated successfully"),

    //Member Payment
    MEMBERSHIP_PAYMENT_CREATED_SUCCESS(200, "Membership payment is created successfully"),
    MEMBERSHIP_INSTALLMENT_PAYMENT_CREATED_SUCCESS(200, "Membership installment payment is created successfully"),

    // General Success
    LOGIN_IS_SUCCESS(200, "Login is successful");

    private final int code;
    private final String message;
}