package com.jafar.api.domain.picture.controller;

import com.jafar.api.domain.member.entity.Member;
import com.jafar.api.domain.picture.entity.Picture;
import com.jafar.api.domain.s3.service.ImageService;
import com.jafar.api.oauth2.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@Tag(name = "사진 업로드")
@RequestMapping("/pictures")
public class PictureController {

    @Autowired
    private ImageService imageService;

    @Operation(summary = "사진 등록")
    @PostMapping(value = "/save" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal CustomOAuth2User member) {
        try {
            Picture uploadedPicture = imageService.saveImage(file, member);
            return ResponseEntity.ok(uploadedPicture.getFileName());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }
}
