package com.auth.service.web.controller;

import com.auth.service.dtos.JwtTokenDTO;
import com.auth.service.dtos.UserLoginDTO;
import com.auth.service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<JwtTokenDTO> authenticate(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        JwtTokenDTO jwtTokenDTO = userService.authenticateUser(userLoginDTO);
        return ResponseEntity.ok(jwtTokenDTO);
    }

}
