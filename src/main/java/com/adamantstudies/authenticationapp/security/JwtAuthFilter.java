package com.adamantstudies.authenticationapp.security;

import com.adamantstudies.authenticationapp.exception.UserNotAuthenticatedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import static com.adamantstudies.authenticationapp.security.Constants.*;

public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    JwtAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            JwtUser jwtUser = mapUserFromRequest(request);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtUser.getUsername(), jwtUser.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new UserNotAuthenticatedException("User not authorized!");
        }
    }

    private JwtUser mapUserFromRequest(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getInputStream(), JwtUser.class);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
        String token = Jwts.builder()
                .setSubject("admin")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes(StandardCharsets.UTF_8))
                .compact();
        response.addHeader(AUTH_HEADER, TOKEN_PREFIX + token);
    }
}
