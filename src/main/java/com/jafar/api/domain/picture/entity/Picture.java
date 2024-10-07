package com.jafar.api.domain.picture.entity;

import com.jafar.api.domain.member.entity.Member;
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

    public Picture(String url, String fileName, Member member) {
        this.url = url;
        this.fileName = fileName;
        this.member = member;
    }
}
