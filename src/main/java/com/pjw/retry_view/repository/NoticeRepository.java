package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    public List<Notice> findByIdLessThanOrderByIdDesc(Long id, Pageable pageable);
    public List<Notice> findAllByOrderByIdDesc(Pageable pageable);
    public Optional<Notice> findById(Long id);
    public Notice save(Notice notice);
    public void deleteById(Long id);
}
