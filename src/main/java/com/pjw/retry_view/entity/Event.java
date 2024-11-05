package com.pjw.retry_view.entity;

import com.pjw.retry_view.dto.EventDTO;
import com.pjw.retry_view.dto.EventImageDTO;
import com.pjw.retry_view.request.WriteBoardRequest;
import com.pjw.retry_view.request.WriteEventRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    @Column(name = "content")
    private String content;
    @Column(name = "view_count")
    @ColumnDefault("0")
    private Long viewCount;
    @Column(name = "start_at")
    private ZonedDateTime startAt;
    @Column(name = "end_at")
    private ZonedDateTime endAt;
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<EventImage> eventImage = new ArrayList<>();

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
                .images(imagesToDTO())
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();
    }

    public void updateEvent(String content, ZonedDateTime startAt, ZonedDateTime endAt, Long updatedBy){
        this.content =content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.updatedBy = updatedBy;
        this.updatedAt = ZonedDateTime.now();
    }

    public static Event newOne(String content, ZonedDateTime startAt, ZonedDateTime endAt, Long createdBy){
        return Event.builder()
                .content(content)
                .viewCount(0L)
                .startAt(startAt)
                .endAt(endAt)
                .eventImage(new ArrayList<EventImage>())
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    @Builder
    public Event(Long id, String content, Long viewCount, ZonedDateTime startAt, ZonedDateTime endAt, List<EventImage> eventImage, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.viewCount = viewCount;
        this.startAt = startAt;
        this.endAt = endAt;
        this.eventImage = eventImage;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    private List<EventImageDTO> imagesToDTO(){
        if(CollectionUtils.isEmpty(eventImage)) return null;
        return eventImage.stream().map(EventImage::toDTO).toList();
    }
}