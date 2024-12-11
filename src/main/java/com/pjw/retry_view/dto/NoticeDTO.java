package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.Image;
import com.pjw.retry_view.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NoticeDTO {
    private Long id;
    private String content;
    private Long viewCount;
    private List<ImageDTO> images;
    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    public static NoticeDTO from(Notice notice, List<Image> images){
        if(images == null) images = new ArrayList<>();

        return NoticeDTO.builder()
                .id(notice.getId())
                .content(notice.getContent())
                .viewCount(notice.getViewCount())
                .images(images.stream().map(ImageDTO::fromEntity).toList())
                .createdBy(notice.getCreatedBy())
                .createdAt(notice.getCreatedAt())
                .updatedBy(notice.getUpdatedBy())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

    @Builder
    public NoticeDTO(Long id, String content, Long viewCount, List<ImageDTO> images, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
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
