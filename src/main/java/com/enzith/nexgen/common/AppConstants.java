package com.enzith.nexgen.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {

    public static final String SECRET_KEY = "abcedefgrqrqrwtwtwueueyryrootpplffkfjfhfhfhhdhdgdfdncncnfkfkflggkhopmmancbfsewt";
    public static final String JWT_HEADER = "Authorization";
    public static final long EXPIRATION_TIME_MILLIS = 1200L; // 20 minutes
    public static final String EMAIL_CLAIM = "email";
    public static final String AUTHORITIES_CLAIM = "authorities";
    public static final String ISSUED_AT = "iat";
    public static final String EXPIRATION = "exp";
    public static final String TOKEN_TYPE = "Bearer";
}
