package com.jafar.api.picture.entity;

import com.jafar.api.member.entity.Member;
import jakarta.persistence.*;

@Entity
public class EditingPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
