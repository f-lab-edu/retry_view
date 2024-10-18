package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.BoardImage;
import com.pjw.retry_view.entity.EventImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventImageDTO {
    private Long id;
    private Long eventId;
    private String imageUrl;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    public static EventImageDTO getImageDTO(String imageUrl){
        return EventImageDTO.builder().imageUrl(imageUrl).build();
    }

    public EventImage toEntity(){
        return EventImage.builder()
                .id(id)
                .imageUrl(imageUrl)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();
    }

    @Builder
    public EventImageDTO(Long id, Long eventId, String imageUrl, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.eventId = eventId;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
}
