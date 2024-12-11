package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.Event;
import com.pjw.retry_view.entity.Image;
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
public class EventDTO {
    private Long id;
    private String content;
    private Long viewCount;
    private ZonedDateTime startAt;
    private ZonedDateTime endAt;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    private List<ImageDTO> images;

    public static EventDTO from(Event event, List<Image> images){
        if(images == null) images = new ArrayList<>();

        return EventDTO.builder()
                .id(event.getId())
                .content(event.getContent())
                .viewCount(event.getViewCount())
                .startAt(event.getStartAt())
                .endAt(event.getEndAt())
                .images(images.stream().map(ImageDTO::fromEntity).toList())
                .createdBy(event.getCreatedBy())
                .createdAt(event.getCreatedAt())
                .updatedBy(event.getUpdatedBy())
                .updatedAt(event.getUpdatedAt())
                .build();
    }
    @Builder
    public EventDTO(Long id, String content, Long viewCount, ZonedDateTime startAt, ZonedDateTime endAt, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt, List<ImageDTO> images) {
        this.id = id;
        this.content = content;
        this.viewCount = viewCount;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.images = images;
    }
}
