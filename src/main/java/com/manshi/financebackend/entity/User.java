package com.manshi.financebackend.entity;

import com.manshi.financebackend.enums.Role;
import com.manshi.financebackend.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")   
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;
}