package com.jafar.api.domain.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.jafar.api.domain.member.entity.Member;
import com.jafar.api.domain.member.repository.MemberRepository;
import com.jafar.api.domain.picture.entity.EditingPicture;
import com.jafar.api.domain.picture.repository.EditingPictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3 amazonS3;
    private final MemberRepository memberRepository;
    private final EditingPictureRepository editingPictureRepository;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public Map<String, String> saveImage(MultipartFile file) throws IOException {
        try {
            // S3에 이미지 업로드
            String fileName = generateFileName(file.getOriginalFilename());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            }

            // S3에 업로드된 이미지의 URL 반환
            String imageUrl = amazonS3.getUrl(bucket, fileName).toString();

            Map<String, String> imageMap = new HashMap<>();
            imageMap.put("fileName", fileName);
            imageMap.put("imageUrl", imageUrl);

            return imageMap;

        } catch (Exception e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }

/*

    public byte[] getImage(String fileName) throws IOException {
        try (S3Object s3Object = amazonS3.getObject(bucket, "userId/" + fileName);
             S3ObjectInputStream inputStream = s3Object.getObjectContent()) {
            return IOUtils.toByteArray(inputStream);
        }
    }

    public void deleteImage(String fileName) {
        String objectKey = "userId/" + fileName;
        amazonS3.deleteObject(bucket, objectKey);
    }
*/


    private String generateFileName(String originalName) {
        return "uuid_" + UUID.randomUUID().toString();
    }

    public List<String> listFiles(String loginId) {
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucket)
                .withPrefix(loginId + "/");

        List<String> fileList = new ArrayList<>();
        ListObjectsV2Result result;

        do {
            result = amazonS3.listObjectsV2(request);

            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                String key = objectSummary.getKey();
                String fileName = key.substring(key.indexOf('/') + 1);
                if (!fileName.isEmpty()) {
                    fileList.add(fileName);
                }
            }

            // 페이지네이션
            String token = result.getNextContinuationToken();
            request.setContinuationToken(token);
        } while (result.isTruncated());

        return fileList;
    }
}

