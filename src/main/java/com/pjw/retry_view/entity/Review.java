package com.pjw.retry_view.entity;

import com.pjw.retry_view.converter.ImageIdsConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "review")
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Integer score;
    private String comment;
    @Convert(converter = ImageIdsConverter.class)
    private List<Long> imageIds;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    public void updateReview(Long productId, Integer score, String comment, List<Long> imageIds, Long updatedBy){
        this.productId = productId;
        this.score = score;
        this.comment = comment;
        this.imageIds = imageIds;
        this.updatedBy = updatedBy;
        this.updatedAt = ZonedDateTime.now();
    }

    public static Review newOne(Long productId, Integer score, String comment, List<Long> imageIds, Long createdBy){
        return Review.builder()
                .productId(productId)
                .score(score)
                .comment(comment)
                .imageIds(imageIds)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    @Builder
    public Review(Long id, Long productId, Integer score, String comment, List<Long> imageIds, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.productId = productId;
        this.score = score;
        this.comment = comment;
        this.imageIds = imageIds;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
}
