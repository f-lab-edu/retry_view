package com.pjw.retry_view.entity;

import com.pjw.retry_view.converter.ImageIdsConverter;
import com.pjw.retry_view.dto.NoticeView;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ColumnDefault("0")
    private Long viewCount;
    @Convert(converter = ImageIdsConverter.class)
    private List<Long> imageIds;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    @Builder
    public Notice(Long id, String title, String content, Long viewCount, List<Long> imageIds, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.imageIds = imageIds;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public static Notice newOne(String title, String content, List<Long> imageIds, Long createdBy){
        return Notice.builder()
                .title(title)
                .content(content)
                .imageIds(imageIds)
                .viewCount(0L)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    public void updateNotice(String title, String content, List<Long> imageIds, Long updateBy){
        this.title = title;
        this.content = content;
        this.imageIds = imageIds;
        this.updatedBy = updateBy;
        this.updatedAt = ZonedDateTime.now();
    }

    public NoticeView toDTO(){
        return NoticeView.builder()
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
