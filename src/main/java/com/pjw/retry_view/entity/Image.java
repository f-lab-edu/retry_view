package com.pjw.retry_view.entity;

import com.pjw.retry_view.converter.ImageTypeConverter;
import com.pjw.retry_view.dto.ImageDTO;
import com.pjw.retry_view.enums.ImageType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    public static Image newOne(Long imageId, String imageUrl, Long createdBy){
        return Image.builder()
                .id(imageId)
                .imageUrl(imageUrl)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    @Builder
    public Image(Long id, String imageUrl, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

}
