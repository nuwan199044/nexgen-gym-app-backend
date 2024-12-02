package com.enzith.nexgen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    USER_NOT_FOUND(500, "User not found"),
    INVALID_CREDENTIALS(400, "Invalid credentials"),
    LOGIN_IS_SUCCESS(400, "login is success");

    private final int code;
    private final String message;
}