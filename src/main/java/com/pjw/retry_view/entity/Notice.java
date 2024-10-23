package com.pjw.retry_view.entity;

import com.pjw.retry_view.dto.NoticeDTO;
import com.pjw.retry_view.dto.NoticeImageDTO;
import com.pjw.retry_view.request.WriteNoticeRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<NoticeImage> noticeImage = new ArrayList<>();

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Builder
    public Notice(Long id, String content, Long viewCount, List<NoticeImage> noticeImage, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.viewCount = viewCount;
        this.noticeImage = noticeImage;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public static Notice newNoticeFromReq(String content, Long createdBy, List<NoticeImage> images){
        return Notice.builder()
                .content(content)
                .viewCount(0L)
                .noticeImage(images)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build()
                .changeNoticeImage();
    }

    public Notice changeNoticeImage(){
        this.noticeImage.forEach((image)->{
            image.setNotice(this);
            image.setCreatedBy(this.createdBy);
            image.setCreatedAt(ZonedDateTime.now());
        });
        return this;
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

    private List<NoticeImageDTO> imagesToDTO(){
        if(CollectionUtils.isEmpty(noticeImage)) return null;
        return noticeImage.stream().map(NoticeImage::toDTO).toList();
    }
}
