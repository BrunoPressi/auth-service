package com.auth.service.config;

import com.auth.service.web.exception.ExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPointConfig implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ExceptionHandler exceptionHandler = new ExceptionHandler(
                LocalDateTime.now(),
                request.getRequestURI(),
                "Invalid password",
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                HttpStatus.UNAUTHORIZED.value()
        );

        response.setContentType("application/json");

        if(authException instanceof BadCredentialsException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(objectMapper.writeValueAsString(exceptionHandler));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Falha na autenticação\"}");
        }

    }
}
