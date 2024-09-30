package com.jafar.api.picture.entity;

import com.jafar.api.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Picture {

    @Id
    private Long id;

    private String url;

    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
