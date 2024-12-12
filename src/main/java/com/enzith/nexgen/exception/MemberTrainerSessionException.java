package com.enzith.nexgen.exception;

import com.enzith.nexgen.enums.ResponseCode;
import lombok.Getter;

@Getter
public class MemberTrainerSessionException extends RuntimeException {
    private final transient ResponseCode error;

    public MemberTrainerSessionException(ResponseCode error) {
        super(error.getMessage());
        this.error = error;
    }
}
