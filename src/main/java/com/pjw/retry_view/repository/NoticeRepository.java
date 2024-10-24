package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    public List<Notice> findAll();
    public Optional<Notice> findById(Long id);
    public Notice save(Notice notice);
}
