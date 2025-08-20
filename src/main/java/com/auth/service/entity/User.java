package com.auth.service.entity;

import com.auth.service.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User implements Serializable {

    @Id
    private String id;

    @Field()
    private String email;

    @Field
    private String password;

    private List<Role> roles = new ArrayList<>();
}
