package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.Image;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class ImageView {
    private Long id;
    private String imageUrl;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    public static ImageView newOne(Long id, String imageUrl, Long createdBy){
        return ImageView.builder()
                .id(id)
                .imageUrl(imageUrl)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    public static ImageView fromEntity(Image image){
        return ImageView.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .createdBy(image.getCreatedBy())
                .createdAt(image.getCreatedAt())
                .updatedBy(image.getUpdatedBy())
                .updatedAt(image.getUpdatedAt())
                .build();
    }

    @Builder
    public ImageView(Long id, String imageUrl, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
}
