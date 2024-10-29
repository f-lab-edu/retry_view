package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.NoticeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeImageRepository extends JpaRepository<NoticeImage, Long> {
    public List<NoticeImage> findAll();
    public List<NoticeImage> findByNoticeId(Long noticeId);
    public NoticeImage save(NoticeImage noticeImage);
    @Modifying
    @Query("delete from EventImage ei where ei.id in :ids")
    public void deleteByIds(@Param("ids") List<Long> ids);
}
