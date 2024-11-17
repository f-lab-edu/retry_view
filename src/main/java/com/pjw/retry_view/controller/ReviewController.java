package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.ReviewView;
import com.pjw.retry_view.request.DeleteRequest;
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
    public List<ReviewView> getReviewListByProductId(@RequestParam(name = "cursor", required = false) Long cursor, @PathVariable("productId") Long productId){
        return reviewService.getReviewListByProductId(cursor,productId);
    }

    @GetMapping("/users/{createdBy}")
    public List<ReviewView> getReviewListByCreatedBy(@RequestParam(name = "cursor", required = false) Long cursor, @PathVariable("createdBy") Long createdBy){
        return reviewService.getReviewListByCreatedBy(cursor,createdBy);
    }

    @PostMapping
    public ReviewView saveReview(@RequestBody @Valid ReviewRequest review){
        return reviewService.saveReview(review);
    }

    @PutMapping
    public ReviewView updateReview(@RequestBody @Valid ReviewRequest review){
        return reviewService.updateReview(review);
    }

    @DeleteMapping
    public void deleteReview(@RequestBody DeleteRequest req){
        reviewService.deleteById(req.getId());
    }
}
