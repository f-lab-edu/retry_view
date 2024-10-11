package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class NoticeDTO {
    private Long id;
    private String content;
    private Long viewCount;
    private List<NoticeImageDTO> images;
    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;


    @Builder
    public NoticeDTO(Long id, String content, Long viewCount, List<NoticeImageDTO> images, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.viewCount = viewCount;
        this.images = images;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public Notice toEntity(){
        return Notice.builder()
                .id(id)
                .content(content)
                .viewCount(viewCount)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();
    }
}
