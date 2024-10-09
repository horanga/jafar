package com.jafar.api.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private String email;

    private String loginId;

    private String userName;

    private String role;

    public Member(String email, String loginId, String userName, String role) {
        this.email = email;
        this.loginId = loginId;
        this.userName = userName;
        this.role = role;
    }
}
