package com.pjw.retry_view.entity;

import com.pjw.retry_view.converter.ImageTypeConverter;
import com.pjw.retry_view.dto.ImageDTO;
import com.pjw.retry_view.dto.ImageType;
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
    @Column(name = "type")
    @Convert(converter = ImageTypeConverter.class)
    private ImageType type;
    @Column(name = "parent_id")
    private Long parentId;
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

    public static Image newOne(Long imageId, ImageType type, Long parentId, String imageUrl, Long createdBy){
        return Image.builder()
                .id(imageId)
                .type(type)
                .parentId(parentId)
                .imageUrl(imageUrl)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    public void changeParentId(Long parentId){
        this.parentId = parentId;
    }

    @Builder
    public Image(Long id, ImageType type, Long parentId, String imageUrl, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.type = type;
        this.parentId = parentId;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public ImageDTO toDTO(){
        return ImageDTO.builder()
                .id(id)
                .type(type)
                .parentId(parentId)
                .imageUrl(imageUrl)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();
    }
}
