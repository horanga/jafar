package com.jafar.api.domain.picture.dto;


import com.jafar.api.domain.picture.entity.EditingPicture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditingPictureResponse {
    private Long id;
    private String url;
    private String fileName;

    public static EditingPictureResponse from(EditingPicture editingPicture) {
        return new EditingPictureResponse(
                editingPicture.getId(),
                editingPicture.getUrl(),
                editingPicture.getFileName()
        );
    }

    public EditingPictureResponse(Long id, String url, String fileName) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
    }

}