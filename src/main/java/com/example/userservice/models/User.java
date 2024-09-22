package com.example.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Entity(name ="users")
@Getter
@Setter
public class User extends BaseModel{

    private String name;
    private String email;

    private String hashedPassword;

    @ManyToMany
    private List<Role> roles;
}
