package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.LoginRequest;
import com.enzith.nexgen.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
