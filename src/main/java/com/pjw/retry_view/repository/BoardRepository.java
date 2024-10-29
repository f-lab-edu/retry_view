package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    public List<Board> findAll();
    public Optional<Board> findById(Long id);
    public Board save(Board board);
    public void deleteById(Long id);
}
