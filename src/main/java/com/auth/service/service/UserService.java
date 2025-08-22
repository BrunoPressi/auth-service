package com.auth.service.service;

import com.auth.service.dtos.JwtTokenDTO;
import com.auth.service.dtos.UserLoginDTO;
import com.auth.service.entity.User;
import com.auth.service.entity.UserDetailsImpl;
import com.auth.service.exceptions.EmailNotFoundException;
import com.auth.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException(String.format("User |%s| not found", email))
        );
    }

    public JwtTokenDTO authenticateUser(UserLoginDTO userLoginDTO) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new JwtTokenDTO(jwtTokenService.generateToken(userDetails));
    }

}