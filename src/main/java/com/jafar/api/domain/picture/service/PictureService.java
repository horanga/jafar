package com.jafar.api.domain.picture.service;


import com.jafar.api.domain.picture.dto.PictureResponse;
import com.jafar.api.domain.picture.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PictureService {

    PictureRepository pictureRepository;

    public Page<PictureResponse> memberList(Long memberId, Pageable pageable) {
        return pictureRepository.findPicturesByMemberId(memberId, pageable);
    }


}