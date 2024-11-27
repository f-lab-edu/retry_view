package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewView {
    private Long id;
    private Long productId;
    private Integer score;
    private String comment;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    private List<ImageDTO> images;

    public static ReviewView fromEntity(Review review){
        return ReviewView.builder()
                .id(review.getId())
                .productId(review.getProductId())
                .score(review.getScore())
                .comment(review.getComment())
                .createdBy(review.getCreatedBy())
                .createdAt(review.getCreatedAt())
                .updatedBy(review.getUpdatedBy())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    @Builder
    public ReviewView(Long id, Long productId, Integer score, String comment, List<ImageDTO> images, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.productId = productId;
        this.score = score;
        this.comment = comment;
        this.images = images;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
}
