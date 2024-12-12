package com.enzith.nexgen.exception;

import com.enzith.nexgen.enums.ResponseCode;
import lombok.Getter;

@Getter
public class TrainerPackageException extends RuntimeException {
    private final transient ResponseCode error;

    public TrainerPackageException(ResponseCode error) {
        super(error.getMessage());
        this.error = error;
    }
}