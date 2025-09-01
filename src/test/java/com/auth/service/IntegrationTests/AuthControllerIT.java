package com.auth.service.IntegrationTests;

import com.auth.service.dtos.JwtTokenDTO;
import com.auth.service.dtos.UserLoginDTO;
import com.auth.service.entity.User;
import com.auth.service.entity.enums.Role;
import com.auth.service.repository.UserRepository;
import com.auth.service.web.exception.ExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        User user = new User("68b09c1b99023bcd54004d28", "bob@gmail.com", "$2a$12$9ofQInwnWXoEb5rThsf2GufFX4UeMgMZEeWwtmElJlN0OIQxn677K", List.of(Role.ROLE_CUSTOMER));
        userRepository.save(user);
    }

    @Test
    public void testAuthSuccess() {
        String email = "bob@gmail.com";
        String password = "bob123456";

        UserLoginDTO userLoginDTO = new UserLoginDTO(email, password);

         JwtTokenDTO responseBody = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userLoginDTO)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(JwtTokenDTO.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
    }

    @Test
    public void testAuthInvalidPassword() {
        String email = "bob@gmail.com";
        String password = "bob1111111";

        UserLoginDTO userLoginDTO = new UserLoginDTO(email, password);

        ExceptionHandler responseBody = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userLoginDTO)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED)
                .expectBody(ExceptionHandler.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getPath()).isEqualTo("/api/v1/auth");
        assertThat(responseBody.getStatusCode()).isEqualTo(401);
        assertThat(responseBody.getStatusMessage()).isEqualTo("Unauthorized");
        assertThat(responseBody.getErrorMessage()).isEqualTo("Invalid password");
    }

    @Test
    public void testAuthNonExistsUser() {
        String email = "boobb@gmail.com";
        String password = "max123456";

        UserLoginDTO userLoginDTO = new UserLoginDTO(email, password);

        ExceptionHandler responseBody = webTestClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userLoginDTO)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(ExceptionHandler.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getPath()).isEqualTo("/api/v1/auth");
        assertThat(responseBody.getStatusCode()).isEqualTo(404);
        assertThat(responseBody.getStatusMessage()).isEqualTo("Not Found");
        assertThat(responseBody.getErrorMessage()).isEqualTo("User |" + email + "| not found");
    }

}
