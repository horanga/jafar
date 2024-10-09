package com.jafar.api.domain.picture.controller;

import com.jafar.api.common.response.ApiResponse;
import com.jafar.api.domain.member.entity.Member;
import com.jafar.api.domain.member.exception.MemberNotFoundException;
import com.jafar.api.domain.picture.dto.PictureResponse;
import com.jafar.api.domain.picture.entity.Picture;
import com.jafar.api.domain.picture.service.PictureService;
import com.jafar.api.domain.s3.service.ImageService;
import com.jafar.api.oauth2.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "사진 업로드")
@RequestMapping("/pictures")
public class PictureController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private PictureService pictureService;

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

    @Operation(summary = "유저별 사진 조회")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<ApiResponse<Page<PictureResponse>>> getPicturesByMemberId(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PictureResponse> pictures = pictureService.memberList(memberId, pageable);
            return ResponseEntity.ok(new ApiResponse<>("success", "Pictures retrieved successfully", pictures));
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("error", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "An error occurred while retrieving pictures", null));
        }
    }

}
