package com.enzith.nexgen.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {

    public static final String SECRET_KEY = "abcedefgrqrqrwtwtwueueyryrootpplffkfjfhfhfhhdhdgdfdncncnfkfkflggkhopmmancbfsewt";
    public static final String JWT_HEADER = "Authorization";
    public static final long EXPIRATION_TIME_MILLIS = 36000L; // 20 minutes
    public static final String EMAIL_CLAIM = "email";
    public static final String AUTHORITIES_CLAIM = "authorities";
    public static final String ISSUED_AT = "iat";
    public static final String EXPIRATION = "exp";
    public static final String TOKEN_TYPE = "Bearer";

    //Notification messages
    public static final String MEMBER_EXPIRATION_NOTIFICATION_MESSAGE =
            "Membership for %s (%s) has expired. Notify the member and assist with renewal.";

    public static final String MEMBER_PENDING_EXPIRATION_NOTIFICATION_MESSAGE =
            "Membership for %s (%s) will expire in 5 days. Notify the member and assist with renewal.";

    public static final String SINGLE_SESSIONS = "SINGLE SESSIONS";
    public static final String OTHER_SESSIONS = "OTHER SESSIONS";
}
