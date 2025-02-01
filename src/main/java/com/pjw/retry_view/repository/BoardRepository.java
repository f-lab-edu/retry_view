package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.Board;
import com.pjw.retry_view.enums.BoardType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {
    public List<Board> findAllByOrderByIdDesc(Pageable pageable);
    public List<Board> findByIdLessThanAndTitleLikeOrderByIdDesc(Long id, String title, Pageable pageable);
    public List<Board> findByIdLessThanAndTypeOrderByIdDesc(Long id, BoardType type, Pageable pageable);
    public List<Board> findByIdIn(Collection<Long> ids);
    public Optional<Board> findById(Long id);
    public Board save(Board board);
    public void deleteById(Long id);
}
