package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    public List<BoardImage> findAll();
    public List<BoardImage> findByBoardId(Long id);
    public BoardImage save(BoardImage boardImage);
    @Modifying
    @Query("delete from EventImage ei where ei.id in :ids")
    public void deleteByIds(@Param("ids") List<Long> ids);
}
