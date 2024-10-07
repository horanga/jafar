package com.jafar.api.domain.picture.repository;

import com.jafar.api.domain.picture.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
