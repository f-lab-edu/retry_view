package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.ReviewView;
import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.enums.ApiResponseCodeExamples;
import com.pjw.retry_view.enums.ErrorCode;
import com.pjw.retry_view.request.DeleteRequest;
import com.pjw.retry_view.request.ReviewRequest;
import com.pjw.retry_view.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "리뷰 관리 API 컨트롤러", description = "리뷰 관리 API")
@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "제품의 리뷰 목록 조회 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.INVALID_TOKEN})
    @GetMapping("/{productId}")
    public List<ReviewView> getReviewListByProductId(@RequestParam(name = "cursor", required = false) Long cursor, @PathVariable("productId") Long productId){
        return reviewService.getReviewListByProductId(cursor,productId);
    }

    @Operation(summary = "유저가 작성한 이뷰 목록 조회 API", description = "")
    @ApiResponseCodeExamples({ ErrorCode.INVALID_TOKEN})
    @GetMapping("/users")
    public List<ReviewView> getReviewListByCreatedBy(@AuthenticationPrincipal UserDetail userDetail, @RequestParam(name = "cursor", required = false) Long cursor){
        return reviewService.getReviewListByCreatedBy(cursor, userDetail.getId());
    }

    @Operation(summary = "리뷰 작성 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @PostMapping
    public ReviewView saveReview(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid ReviewRequest review){
        review.setCreatedBy(userDetail.getId());
        return reviewService.saveReview(review);
    }

    @Operation(summary = "리뷰 수정 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.NOT_MY_RESOURCE, ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @PutMapping
    public ReviewView updateReview(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid ReviewRequest review){
        review.setUpdatedBy(userDetail.getId());
        return reviewService.updateReview(review);
    }

    @Operation(summary = "리뷰 삭제 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.NOT_MY_RESOURCE, ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @DeleteMapping
    public void deleteReview(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid DeleteRequest req){
        reviewService.deleteById(req.getId(), userDetail.getId());
    }
}
