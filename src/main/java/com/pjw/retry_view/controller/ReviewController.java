package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.Reviews;
import com.pjw.retry_view.request.ReviewRequest;
import com.pjw.retry_view.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{productId}")
    public List<Reviews> getReviewListByProductId(@PathVariable("productId") Long productId){
        return reviewService.getReviewListByProductId(productId);
    }

    @GetMapping("/users/{createdBy}")
    public List<Reviews> getReviewListByCreatedBy(@PathVariable("createdBy") Long createdBy){
        return reviewService.getReviewListByCreatedBy(createdBy);
    }

    @PostMapping
    public Reviews saveReview(@RequestBody @Valid ReviewRequest review){
        return reviewService.saveReview(review);
    }

    @PutMapping("/{id}")
    public Reviews updateReview(@PathVariable("id") Long id, @RequestBody @Valid ReviewRequest review){
        return reviewService.updateReview(review, id);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("id") Long id){
        reviewService.deleteById(id);
    }
}
