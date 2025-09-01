package com.auth.service.web.controller;

import com.auth.service.dtos.JwtTokenDTO;
import com.auth.service.dtos.UserLoginDTO;
import com.auth.service.service.UserService;
import com.auth.service.web.exception.ExceptionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @Operation(summary = "authenticate user", description = "Endpoint to authenticate user with JWT Token",
        responses = {
            @ApiResponse(responseCode = "201", description = "authenticate succesfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtTokenDTO.class))),
            @ApiResponse(responseCode = "404", description = "user not found",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "{\n" +
                                    "  \"timestamp\": \"2025-09-01T17:49:33.713Z\",\n" +
                                    "  \"path\": \"/api/auth/login\",\n" +
                                    "  \"errorMessage\": \"User |user@email.com| not found\",\n" +
                                    "  \"statusMessage\": \"Not Found\",\n" +
                                    "  \"statusCode\": 404,\n" +
                                    "  \"erros\": {}\n" +
                                    "}"
                    ))),
            @ApiResponse(responseCode = "401", description = "invalid password",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(
                            value = "{\n" +
                                    "  \"timestamp\": \"2025-09-01T17:49:33.713Z\",\n" +
                                    "  \"path\": \"/api/auth/login\",\n" +
                                    "  \"errorMessage\": \"Invalid password\",\n" +
                                    "  \"statusMessage\": \"Unauthorized\",\n" +
                                    "  \"statusCode\": 401,\n" +
                                    "  \"erros\": {}\n" +
                                    "}"
                    )))
        })
    @PostMapping
    public ResponseEntity<JwtTokenDTO> authenticate(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        JwtTokenDTO jwtTokenDTO = userService.authenticateUser(userLoginDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(jwtTokenDTO);
    }

}
