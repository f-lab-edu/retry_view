package com.pjw.retry_view.entity;

import com.pjw.retry_view.converter.BoardTypeEnumConverter;
import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.dto.BoardImageDTO;
import com.pjw.retry_view.dto.BoardType;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "board")
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    @Convert(converter = BoardTypeEnumConverter.class)
    private BoardType type;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "content")
    private String content;
    @Column(name = "view_count")
    private Long viewCount;
    @Column(name = "price")
    private Long price;
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<BoardImage> boardImage = new ArrayList<>();

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public BoardDTO toDTO(){
        return BoardDTO.builder()
                .id(id)
                .type(type)
                .productId(productId)
                .content(content)
                .viewCount(viewCount)
                .price(price)
                .images(imagesToDTO())
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();
    }

    private List<BoardImageDTO> imagesToDTO(){
        if(boardImage == null) return null;
        return boardImage.stream().map(BoardImage::toDTO).toList();
    }
}
