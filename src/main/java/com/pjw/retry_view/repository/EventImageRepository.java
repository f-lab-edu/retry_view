package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventImageRepository extends JpaRepository<EventImage, Long> {
    public List<EventImage> findAll();
    public List<EventImage> findByEventId(Long eventId);
    public EventImage save(EventImage EventImage);
    @Modifying
    @Query("delete from EventImage ei where ei.id in :ids")
    public void deleteByIds(@Param("ids") List<Long> ids);
}
