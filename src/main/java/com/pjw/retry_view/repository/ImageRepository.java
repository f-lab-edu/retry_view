package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    public List<Image> findByIdIn(List<Long> ids);
    public Image save(Image image);
    public void deleteByIdIn(List<Long> ids);
}
