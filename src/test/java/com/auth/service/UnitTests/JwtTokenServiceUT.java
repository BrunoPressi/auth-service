package com.auth.service.UnitTests;

import com.auth.service.entity.User;
import com.auth.service.entity.UserDetailsImpl;
import com.auth.service.entity.enums.Role;
import com.auth.service.service.JwtTokenService;
import com.auth.service.utils.JwtGenerator;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class JwtTokenServiceUT {

    JwtTokenService tokenService;

    UserDetailsImpl userDetails;

    String notExpiredToken;

    String expiredToken;

    @BeforeEach
    public void init() {
        List<Role> roles;
        roles = List.of(Role.ROLE_CUSTOMER, Role.ROLE_ADMIN);

        User user = new User(
                "6613f6c8f51bb4362ee2a4d",
                "test@email.com",
                "123456",
                roles
        );

        userDetails = new UserDetailsImpl(user);
        tokenService = new JwtTokenService();

        notExpiredToken = JwtGenerator.generateNotExpiredJWT(userDetails);
        expiredToken = JwtGenerator.generateExpiredJWT(userDetails);
    }

    @Test
    public void generateToken() {
        String token = tokenService.generateToken(userDetails);

        String subject = JWT.decode(token).getSubject();
        Map<String, Claim> claims = JWT.decode(token).getClaims();

        List<Role> roles = claims.get("roles").asList(Role.class);

        Assertions.assertNotNull(token);
        Assertions.assertEquals(userDetails.getUsername(), subject);
        Assertions.assertTrue(roles.contains(Role.ROLE_CUSTOMER));
        Assertions.assertTrue(roles.contains(Role.ROLE_ADMIN));
    }

    @Test()
    public void getSubjectFromValidToken() {
        String subject = tokenService.getSubjectFromToken(notExpiredToken);

        Assertions.assertNotNull(subject);
        Assertions.assertEquals(subject, userDetails.getUsername());
    }

    @Test
    public void getSubjectFromExpiredToken() {
        Assertions.assertThrows(JWTVerificationException.class, () -> tokenService.getSubjectFromToken(expiredToken));
    }

}
