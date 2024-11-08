package com.pjw.retry_view.dto;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private BoardType type;
    private Long productId;
    private String title;
    private String content;
    private Long viewCount;
    private Long price;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    private List<ImageDTO> images;

    @Builder
    public BoardDTO(Long id, BoardType type, Long productId, String content, Long viewCount, Long price, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt, List<ImageDTO> images) {
        this.id = id;
        this.type = type;
        this.productId = productId;
        this.content = content;
        this.viewCount = viewCount;
        this.price = price;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.images = images;
    }

}
