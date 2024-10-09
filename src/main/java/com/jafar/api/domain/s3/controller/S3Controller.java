package com.jafar.api.domain.s3.controller;

import com.jafar.api.domain.picture.entity.EditingPicture;
import com.jafar.api.domain.s3.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    @Autowired
    private ImageService imageService;

//    @PostMapping("/save")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("loginId") Long loginId) {
//        try {
//            EditingPicture uploadedPicture = imageService.saveImage(file, loginId);
//            return ResponseEntity.ok(uploadedPicture.getFileName());
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
//        }
//    }

/*
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            byte[] data = imageUploadService.getImage(fileName);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(data);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        try {
            imageUploadService.deleteImage(fileName);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Delete failed: " + e.getMessage());
        }
    }


 */

    @GetMapping("/list/{loginId}")
    public ResponseEntity<List<String>> listFiles(@PathVariable String loginId) {
        try {
            List<String> fileNames = imageService.listFiles(loginId);
            return ResponseEntity.ok(fileNames);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }



}
