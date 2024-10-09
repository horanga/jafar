package com.jafar.api.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Setter
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String providerId;

    private String userName;

    private String role;

    public Member(String email, String providerId, String userName, String role) {
        this.email = email;
        this.providerId = providerId;
        this.userName = userName;
        this.role = role;
    }
}
