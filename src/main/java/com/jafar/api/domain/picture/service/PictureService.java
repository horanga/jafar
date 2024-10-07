package com.jafar.api.domain.picture.service;


import com.jafar.api.domain.member.entity.Member;
import com.jafar.api.domain.member.repository.MemberRepository;
import com.jafar.api.domain.picture.entity.Picture;
import com.jafar.api.domain.picture.repository.PictureRepository;
import com.jafar.api.domain.s3.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PictureService {

    private final PictureRepository pictureRepository;

    private final MemberRepository memberRepository;

    private final ImageUploadService imageUploadService;

    public void create(MultipartFile file){
        String fileName = null;
        try {
             fileName = imageUploadService.uploadImage(file);
        } catch (IOException e) {

        }
//
//        Picture picture = new Picture("url", fileName, member);
//        pictureRepository.save(picture);
    }
}
