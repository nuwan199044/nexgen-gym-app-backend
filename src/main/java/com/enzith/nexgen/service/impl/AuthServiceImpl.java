package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.config.JwtProvider;
import com.enzith.nexgen.dto.response.AccessToken;
import com.enzith.nexgen.dto.request.LoginRequest;
import com.enzith.nexgen.dto.response.LoginResponse;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.enzith.nexgen.common.AppConstants.EXPIRATION_TIME_MILLIS;
import static com.enzith.nexgen.common.AppConstants.TOKEN_TYPE;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtProvider.generateToken(authentication);
        AccessToken accessToken = AccessToken.builder()
                .accessToken(jwtToken)
                .tokenType(TOKEN_TYPE)
                .expiresIn(EXPIRATION_TIME_MILLIS)
                .issuedAt(new Date())
                .build();
        return LoginResponse.builder()
                .token(accessToken)
                .build();
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
        if (userDetails == null || !password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException(ResponseCode.INVALID_CREDENTIALS.getMessage());
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }
}
