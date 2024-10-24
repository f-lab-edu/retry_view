package com.pjw.retry_view.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class NoticeImageDTO {
    private Long id;
    private Long boardId;
    private String imageUrl;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    public static NoticeImageDTO getImageDTO(String imageUrl){
        return NoticeImageDTO.builder().imageUrl(imageUrl).build();
    }

    @Builder
    public NoticeImageDTO(Long id, Long boardId, String imageUrl, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.boardId = boardId;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
}
