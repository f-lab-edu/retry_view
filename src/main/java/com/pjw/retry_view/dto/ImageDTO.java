package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.Image;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class ImageDTO {
    private Long id;
    private String imageUrl;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    public static ImageDTO newOne(Long id, String imageUrl, Long createdBy){
        return ImageDTO.builder()
                .id(id)
                .imageUrl(imageUrl)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    public static ImageDTO fromEntity(Image image){
        return ImageDTO.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .createdBy(image.getCreatedBy())
                .createdAt(image.getCreatedAt())
                .updatedBy(image.getUpdatedBy())
                .updatedAt(image.getUpdatedAt())
                .build();
    }

    @Builder
    public ImageDTO(Long id, String imageUrl, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
}
