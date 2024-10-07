package com.jafar.api.picture.repository;

import com.jafar.api.picture.entity.EditingPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditingPictureRepository extends JpaRepository<EditingPicture, Long> {
}