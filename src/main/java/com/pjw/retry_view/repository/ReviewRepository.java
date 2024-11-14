package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    public List<Review> findByProductId(Long productId, Pageable pageable);
    public List<Review> findByCreatedBy(Long createdBy, Pageable pageable);
    public Optional<Review> findById(Long id);
    public Review save(Review review);
    public void deleteById(Long id);
}
