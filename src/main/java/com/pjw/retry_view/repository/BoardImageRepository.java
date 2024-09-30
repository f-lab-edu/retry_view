package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    public List<BoardImage> findAll();
    public BoardImage save(BoardImage boardImage);
}
