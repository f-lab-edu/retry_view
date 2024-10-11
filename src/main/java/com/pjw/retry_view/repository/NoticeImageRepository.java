package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.NoticeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeImageRepository extends JpaRepository<NoticeImage, Long> {
    public List<NoticeImage> findAll();
    public NoticeImage save(NoticeImage noticeImage);
}
