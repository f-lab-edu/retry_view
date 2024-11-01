package com.pjw.retry_view.entity;

import com.pjw.retry_view.dto.NoticeImageDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "notice_image")
@Entity
public class NoticeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Builder
    public NoticeImage(Long id, Notice notice, String imageUrl, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.notice = notice;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public void changeNotice(Notice notice){
        notice.getNoticeImage().add(this);
        this.notice = notice;
    }

    public static NoticeImage newOne(Long id, String url, Long createdBy){
        return NoticeImage.builder()
                .id(id)
                .imageUrl(url)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    public NoticeImageDTO toDTO(){
        return NoticeImageDTO.builder()
                .id(id)
                .imageUrl(imageUrl)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();
    }
}
