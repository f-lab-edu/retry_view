package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    public List<Review> findByProductId(Long productId);
    public List<Review> findByCreatedBy(Long createdBy);
    public Optional<Review> findById(Long id);
    public Review save(Review review);
    public void deleteById(Long id);
}
