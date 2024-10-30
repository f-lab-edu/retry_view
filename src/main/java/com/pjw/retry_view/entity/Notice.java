package com.pjw.retry_view.entity;

import com.pjw.retry_view.dto.ImageDTO;
import com.pjw.retry_view.dto.NoticeDTO;
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

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "view_count")
    @ColumnDefault("0")
    private Long viewCount;

    @Transient
    private List<Image> images = new ArrayList<>();

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Builder
    public Notice(Long id, String content, Long viewCount, List<Image> images, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.viewCount = viewCount;
        this.images = images;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public static Notice newOne(String content, Long createdBy){
        return Notice.builder()
                .content(content)
                .viewCount(0L)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    public void updateNotice(String content, Long updateBy){
        this.content = content;
        this.updatedBy = updateBy;
        this.updatedAt = ZonedDateTime.now();
    }

    public void changeImage(List<Image> images){
        this.images = images;
    }

    public NoticeDTO toDTO(){
        return NoticeDTO.builder()
                .id(id)
                .content(content)
                .viewCount(viewCount)
                .images(imagesToDTO())
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();
    }

    private List<ImageDTO> imagesToDTO(){
        if(CollectionUtils.isEmpty(images)) return null;
        return images.stream().map(Image::toDTO).toList();
    }
}
