package com.jafar.api.domain.picture.controller;

import com.jafar.api.domain.picture.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pictures")
public class PictureController {

    private final PictureService pictureService;

    @PostMapping
    public void create(MultipartFile multipartFile){
        pictureService.create(multipartFile);

    }
}
