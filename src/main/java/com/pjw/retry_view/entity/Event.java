package com.pjw.retry_view.entity;

import com.pjw.retry_view.converter.ImageIdsConverter;
import com.pjw.retry_view.dto.EventDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "view_count")
    @ColumnDefault("0")
    private Long viewCount;
    @Column(name = "start_at")
    private ZonedDateTime startAt;
    @Column(name = "end_at")
    private ZonedDateTime endAt;
    @Column(name = "image_ids")
    @Convert(converter = ImageIdsConverter.class)
    private List<Long> imageIds;

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public EventDTO toDTO(){
        return EventDTO.builder()
                .id(id)
                .content(content)
                .viewCount(viewCount)
                .startAt(startAt)
                .endAt(endAt)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();
    }

    public void updateEvent(String title, String content, List<Long> imageIds, ZonedDateTime startAt, ZonedDateTime endAt, Long updatedBy){
        this.title = title;
        this.content =content;
        this.imageIds = imageIds;
        this.startAt = startAt;
        this.endAt = endAt;
        this.updatedBy = updatedBy;
        this.updatedAt = ZonedDateTime.now();
    }

    public static Event newOne(String title, String content, List<Long> imageIds, ZonedDateTime startAt, ZonedDateTime endAt, Long createdBy){
        return Event.builder()
                .title(title)
                .content(content)
                .imageIds(imageIds)
                .viewCount(0L)
                .startAt(startAt)
                .endAt(endAt)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    @Builder
    public Event(Long id, String title, String content, List<Long> imageIds, Long viewCount, ZonedDateTime startAt, ZonedDateTime endAt, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageIds = imageIds;
        this.viewCount = viewCount;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

}
