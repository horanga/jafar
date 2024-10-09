package com.jafar.api.domain.picture.service;


import com.jafar.api.domain.picture.dto.PictureResponse;
import com.jafar.api.domain.picture.repository.PictureRepository;
import com.jafar.api.oauth2.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PictureService {

    private final PictureRepository pictureRepository;

    public Page<PictureResponse> memberList(CustomOAuth2User member, Pageable pageable) {

        return pictureRepository.findPicturesByMember(member.getName(), pageable);
    }


}