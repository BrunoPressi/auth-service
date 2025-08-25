package com.auth.service.serviceTests;

import com.auth.service.entity.User;
import com.auth.service.entity.enums.Role;
import com.auth.service.exceptions.EmailNotFoundException;
import com.auth.service.repository.UserRepository;
import com.auth.service.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void findByEmailWithValidEmail() {

        String email = "test@gmail.com";
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_CUSTOMER);

        User user = new User(
                "6613f6c8f51bb4362ee2a4d",
                "test@email.com",
                "123456",
                roles
        );

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        User result = userService.findByEmail(email);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("6613f6c8f51bb4362ee2a4d", result.getId());
        Assertions.assertEquals("test@email.com", result.getEmail());
        Assertions.assertEquals(roles, user.getRoles());
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }

    @Test
    public void findByEmailWithNoExistsEmail() {
        String email = "test@email.com";

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(EmailNotFoundException.class, () -> userService.findByEmail(email));
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
    }

}
