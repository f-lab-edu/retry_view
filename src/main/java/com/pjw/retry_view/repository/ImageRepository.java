package com.pjw.retry_view.repository;

import com.pjw.retry_view.enums.ImageType;
import com.pjw.retry_view.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    public List<Image> findAll();
    public List<Image> findByTypeAndParentId(ImageType type, Long parentId);
    public Image save(Image image);
    @Modifying
    @Query("delete from Image ei where ei.id in :ids")
    public void deleteByIds(@Param("ids") List<Long> ids);
}
