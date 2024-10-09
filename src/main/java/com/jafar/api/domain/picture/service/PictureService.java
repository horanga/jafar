package com.jafar.api.domain.picture.service;


import com.jafar.api.domain.member.entity.Member;
import com.jafar.api.domain.member.repository.MemberRepository;
import com.jafar.api.domain.picture.dto.EditingPictureResponse;
import com.jafar.api.domain.picture.entity.EditingPicture;
import com.jafar.api.domain.picture.entity.Picture;
import com.jafar.api.domain.picture.repository.EditingPictureRepository;
import com.jafar.api.domain.picture.repository.PictureRepository;
import com.jafar.api.domain.s3.service.ImageService;
import com.jafar.api.domain.s3.service.ImageUploadService;
import com.jafar.api.oauth2.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PictureService {

    private final EditingPictureRepository editingPictureRepository;

    private final MemberRepository memberRepository;

    private final ImageService imageService;



    @Transactional
    public EditingPictureResponse saveImage(MultipartFile file) throws IOException {
        CustomOAuth2User principal = (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();

        Member member = memberRepository.findByUserName(username);
        if (member == null) {
            throw new RuntimeException("Member not found with username: " + username);
        }


        Map<String, String> imageMap = imageService.saveImage(file);

        EditingPicture editingPicture = EditingPicture.builder()
                .url(imageMap.get("imageUrl"))
                .fileName(imageMap.get("fileName"))
                .member(member)
                .build();

        EditingPicture savedPicture = editingPictureRepository.save(editingPicture);

        return EditingPictureResponse.from(savedPicture);
    }

    public Page<EditingPictureResponse> getPicturesByMemberId(Long memberId, Pageable pageable) {
        return editingPictureRepository.findPicturesByMemberId(memberId, pageable);
    }





}