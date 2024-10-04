package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    public List<Board> findAll();
    public Board save(Board board);
}
