package com.example.demo.Security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.Services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;


import java.io.IOException;
import java.util.Collections;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenService jwtUtil = new TokenService();

    private static final String HEADER_FIELD_PREPEND = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extract the header containing the JWT
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Check that the request contained a token.
        if (header == null || !header.startsWith(HEADER_FIELD_PREPEND)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Decode the token
        String encodedToken = header.substring(HEADER_FIELD_PREPEND.length());
        DecodedJWT token = jwtUtil.decodeToken(encodedToken);

        // Skip elevation if the token could not be decoded, or it has expired.
        if (token == null || jwtUtil.isTokenExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // The user seems to deserve elevation!

        // Extract the username
        final String username = token.getSubject();

        // Upgrade session privileges
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("USER"))));

        filterChain.doFilter(request, response);
    }
}