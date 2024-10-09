package com.jafar.api.domain.picture.repository;

import com.jafar.api.domain.picture.dto.PictureResponse;
import com.jafar.api.domain.picture.entity.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    @Query("SELECT new com.jafar.api.domain.picture.dto.PictureResponse(ep.id, ep.url, ep.fileName) " +
            "FROM Picture ep " +
            "WHERE ep.member.email = :email " +
            "ORDER BY ep.id DESC")
    Page<PictureResponse> findPicturesByMember(@Param("email") String email, Pageable pageable);

}