package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.Review;
import com.pjw.retry_view.repository.ProductRepository;
import com.pjw.retry_view.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    public Review save(){
        return null;
    }

    public void deleteById(Long id){
        reviewRepository.deleteById(id);
    }
}
