package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.response.APIResponse;
import com.enzith.nexgen.dto.request.LoginRequest;
import com.enzith.nexgen.dto.response.LoginResponse;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.AuthService;
import com.enzith.nexgen.utility.APIResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.LOGIN_IS_SUCCESS,
                        authService.login(loginRequest)
                ), HttpStatus.OK);
    }

}
