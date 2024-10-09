package com.jafar.api.domain.picture.dto;


import com.jafar.api.domain.picture.entity.Picture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PictureResponse {
    private Long id;
    private String url;
    private String fileName;

    public static PictureResponse from(Picture picture) {
        return new PictureResponse(
                picture.getId(),
                picture.getUrl(),
                picture.getFileName()
        );
    }

    public PictureResponse(Long id, String url, String fileName) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
    }

}