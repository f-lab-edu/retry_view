package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.ReviewView;
import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.request.DeleteRequest;
import com.pjw.retry_view.request.ReviewRequest;
import com.pjw.retry_view.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/users")
    public List<ReviewView> getReviewListByCreatedBy(@AuthenticationPrincipal UserDetail userDetail, @RequestParam(name = "cursor", required = false) Long cursor){
        return reviewService.getReviewListByCreatedBy(cursor, userDetail.getId());
    }

    @PostMapping
    public ReviewView saveReview(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid ReviewRequest review){
        review.setCreatedBy(userDetail.getId());
        return reviewService.saveReview(review);
    }

    @PutMapping
    public ReviewView updateReview(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid ReviewRequest review){
        review.setUpdatedBy(userDetail.getId());
        return reviewService.updateReview(review);
    }

    @DeleteMapping
    public void deleteReview(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid DeleteRequest req){
        reviewService.deleteById(req.getId(), userDetail.getId());
    }
}
