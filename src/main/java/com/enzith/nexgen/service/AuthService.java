package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.request.LoginRequest;
import com.enzith.nexgen.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
