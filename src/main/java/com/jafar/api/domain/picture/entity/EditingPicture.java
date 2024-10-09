package com.jafar.api.domain.picture.entity;

import com.jafar.api.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditingPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}