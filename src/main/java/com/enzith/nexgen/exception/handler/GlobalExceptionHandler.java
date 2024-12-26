package com.enzith.nexgen.exception.handler;

import com.enzith.nexgen.dto.response.ErrorDetailResponse;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.exception.MemberException;
import com.enzith.nexgen.exception.MemberMembershipException;
import com.enzith.nexgen.exception.MemberTrainerSessionException;
import com.enzith.nexgen.exception.MembershipTypeException;
import com.enzith.nexgen.exception.PersonalTrainingException;
import com.enzith.nexgen.exception.TrainerException;
import com.enzith.nexgen.exception.TrainerPackageException;
import com.enzith.nexgen.exception.UserProfileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserProfileException.class})
    public ResponseEntity<Object> handleUserProfileException(UserProfileException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(
                ex,
                getErrorDetailResponse(ex.getError(), request, null),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = {MemberException.class})
    public ResponseEntity<Object> handleMemberException(MemberException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(
                ex,
                getErrorDetailResponse(ex.getError(), request, null),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = {TrainerException.class})
    public ResponseEntity<Object> handleTrainerException(TrainerException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(
                ex,
                getErrorDetailResponse(ex.getError(), request, null),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = {MembershipTypeException.class})
    public ResponseEntity<Object> handleMembershipTypeException(MembershipTypeException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(
                ex,
                getErrorDetailResponse(ex.getError(), request, null),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = {TrainerPackageException.class})
    public ResponseEntity<Object> handleTrainerPackageException(TrainerPackageException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(
                ex,
                getErrorDetailResponse(ex.getError(), request, null),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = {MemberTrainerSessionException.class})
    public ResponseEntity<Object> handleMemberTrainerSessionException(MemberTrainerSessionException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(
                ex,
                getErrorDetailResponse(ex.getError(), request, null),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = {PersonalTrainingException.class})
    public ResponseEntity<Object> handlePersonalTrainingException(PersonalTrainingException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(
                ex,
                getErrorDetailResponse(ex.getError(), request, null),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = {MemberMembershipException.class})
    public ResponseEntity<Object> handleMemberMembershipException(MemberMembershipException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(
                ex,
                getErrorDetailResponse(ex.getError(), request, null),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleUnhandledException(Exception ex, WebRequest request) {
        return handleExceptionWithoutResponseBody(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.UNHANDLED_EXCEPTION);
    }

    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return handleExceptionInternal(
                ex,
                getErrorDetailResponse(ResponseCode.METHOD_ARGUMENT_NOT_VALID, request, errors),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        return handleExceptionInternal(
                ex,
                getErrorDetailResponse(ResponseCode.HTTP_MESSAGE_NOT_READABLE, request, null),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    private ResponseEntity<Object> handleExceptionWithoutResponseBody(Exception ex, WebRequest request, HttpStatus httpStatus, ResponseCode errorCode) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(
                ex,
                getErrorDetailResponse(errorCode, request, null),
                new HttpHeaders(),
                httpStatus,
                request
        );
    }

    private ErrorDetailResponse getErrorDetailResponse(ResponseCode error, WebRequest request, Map<String, String> errors) {
        return ErrorDetailResponse.builder()
                .status(error.getCode())
                .error(error.getMessage())
                .timestamp(new Date())
                .path(request.getContextPath() +
                        Objects.requireNonNull(request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, 0)))
                .errorData(errors)
                .build();
    }

}
