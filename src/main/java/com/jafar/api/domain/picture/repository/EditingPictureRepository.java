package com.jafar.api.domain.picture.repository;

import com.jafar.api.domain.picture.dto.EditingPictureResponse;
import com.jafar.api.domain.picture.entity.EditingPicture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditingPictureRepository extends JpaRepository<EditingPicture, Long> {

    @Query("SELECT new com.jafar.api.domain.picture.dto.EditingPictureResponse(ep.id, ep.url, ep.fileName) " +
            "FROM EditingPicture ep " +
            "WHERE ep.member.id = :memberId " +
            "ORDER BY ep.id DESC")
    Page<EditingPictureResponse> findPicturesByMemberId(@Param("memberId") Long memberId, Pageable pageable);
}