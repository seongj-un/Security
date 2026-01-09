package com.example.securityfinal.user;

import com.google.errorprone.annotations.InlineMeValidationDisabled;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false ,name = "user_id")
    private Long id;

    @Column(nullable = false, name= "user_name",length = 32)
    private String userName;

    @Column(nullable = false,name="user_password",length=2508)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role =  Role.USER;

    public User(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role=role;
    }
}
