package com.pjw.retry_view.entity;

import com.pjw.retry_view.converter.BoardTypeEnumConverter;
import com.pjw.retry_view.converter.ImageIdsConverter;
import com.pjw.retry_view.dto.BoardView;
import com.pjw.retry_view.enums.BoardType;
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
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "view_count")
    @ColumnDefault("0")
    private Long viewCount;
    @Column(name = "price")
    private Long price;
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

    public BoardView toDTO(){
        return BoardView.builder()
                .id(id)
                .type(type)
                .productId(productId)
                .title(title)
                .content(content)
                .viewCount(viewCount)
                .price(price)
                //.imageIds(imageIds)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();
    }

    @Builder
    public Board(Long id, BoardType type, Long productId, String title, String content, Long viewCount, Long price, List<Long> imageIds, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.type = type;
        this.productId = productId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.price = price;
        this.imageIds = imageIds;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public static Board newOne(BoardType type, Long productId, String title, String content, Long price, List<Long> imageIds, Long createdBy){
        return Board.builder()
                .type(type)
                .productId(productId)
                .title(title)
                .content(content)
                .viewCount(0L)
                .price(price)
                .imageIds(imageIds)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    public void updateBoard(Long id, BoardType type, Long productId, String title, String content, Long price, List<Long> imageIds, Long updatedBy){
        this.id = id;
        this.type = type;
        this.productId = productId;
        this.title = title;
        this.content = content;
        this.price = price;
        this.imageIds = imageIds;
        this.updatedBy = updatedBy;
        this.updatedAt = ZonedDateTime.now();
    }

}
