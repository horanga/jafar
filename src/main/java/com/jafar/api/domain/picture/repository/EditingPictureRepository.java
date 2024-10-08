package com.jafar.api.domain.picture.repository;

import com.jafar.api.domain.picture.entity.EditingPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditingPictureRepository extends JpaRepository<EditingPicture, Long> {
}