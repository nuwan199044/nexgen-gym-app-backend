package com.enzith.nexgen.config;

import com.enzith.nexgen.common.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveJwtFromRequest(request);

        if (jwt != null) {
            try {
                Claims claims = parseJwt(jwt);
                setAuthentication(claims);
            } catch (Exception ex) {
                throw new BadCredentialsException("Invalid Token", ex);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String resolveJwtFromRequest(HttpServletRequest request) {
        String jwt = request.getHeader(AppConstants.JWT_HEADER);
        if (jwt != null && jwt.startsWith("Bearer ")) {
            return jwt.substring(7);  // Remove 'Bearer ' prefix
        }
        return null;
    }

    private Claims parseJwt(String jwt) {
        SecretKey secretKey = Keys.hmacShaKeyFor(AppConstants.SECRET_KEY.getBytes());
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private void setAuthentication(Claims claims) {
        String email = (String) claims.get("email");
        String authorities = (String) claims.get("authorities");

        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
