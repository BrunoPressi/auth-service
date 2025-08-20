package com.auth.service.entity;

import com.auth.service.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class User {
    private String email;
    private String password;
    private List<Role> roles = new ArrayList<>();
}
