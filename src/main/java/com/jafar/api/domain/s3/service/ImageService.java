package com.jafar.api.domain.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.jafar.api.domain.member.entity.Member;
import com.jafar.api.domain.member.repository.MemberRepository;
import com.jafar.api.domain.picture.entity.Picture;
import com.jafar.api.domain.picture.repository.PictureRepository;
import com.jafar.api.oauth2.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3 amazonS3;
    private final MemberRepository memberRepository;
    private final PictureRepository pictureRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public Picture saveImage(MultipartFile file, CustomOAuth2User oauth2Member) throws IOException {
        Member member = memberRepository.findByLoginId(oauth2Member.getUsername());
        try {

            String fileName = member.getUserName() + "/" + generateFileName(file.getOriginalFilename());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata));
            }

            String imageUrl = amazonS3.getUrl(bucket, fileName).toString();

            // EditingPicture 저장
            Picture picture = new Picture();
            picture.setUrl(imageUrl);
            picture.setFileName(fileName);
            picture.setMember(member);

            return pictureRepository.save(picture);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save image for loginId: " + oauth2Member.getName(), e);
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

