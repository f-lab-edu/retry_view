package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.BoardLike;
import com.pjw.retry_view.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, LikeId> {
    public List<BoardLike> findByIdUserId(Long userId);
    public List<BoardLike> findByBoardId(Long boardId);
    public BoardLike save(BoardLike like);
    public void deleteById(LikeId id);
}
