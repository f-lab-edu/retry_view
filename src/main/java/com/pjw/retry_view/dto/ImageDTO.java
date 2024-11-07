package com.pjw.retry_view.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class ImageDTO {
    private Long id;
    private ImageType type;
    private Long parentId;
    private String imageUrl;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    public static ImageDTO newOne(Long id, ImageType type, Long parentId, String imageUrl, Long createdBy){
        return ImageDTO.builder().build();
    }

    @Builder
    public ImageDTO(Long id, ImageType type, Long parentId, String imageUrl, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.type = type;
        this.parentId = parentId;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
}
