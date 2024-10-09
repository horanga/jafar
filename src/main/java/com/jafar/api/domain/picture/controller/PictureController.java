package com.jafar.api.domain.picture.controller;

import com.jafar.api.common.response.ApiResponse;
import com.jafar.api.domain.member.exception.MemberNotFoundException;
import com.jafar.api.domain.picture.dto.EditingPictureResponse;
import com.jafar.api.domain.picture.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pictures")
public class PictureController {

    private final PictureService pictureService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<EditingPictureResponse>> savePicture(
            @RequestParam("file") MultipartFile file) {
        try {
            EditingPictureResponse response = pictureService.saveImage(file);
            return ResponseEntity.ok(new ApiResponse<>("success", "Image saved successfully", response));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Failed to upload image: " + e.getMessage(), null));
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<ApiResponse<Page<EditingPictureResponse>>> getPicturesByMemberId(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<EditingPictureResponse> pictures = pictureService.getPicturesByMemberId(memberId, pageable);
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
