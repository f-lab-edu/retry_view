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
    @Convert(converter = BoardTypeEnumConverter.class)
    private BoardType type;
    private Long productId;
    private String title;
    private String content;
    @ColumnDefault("0")
    private Long viewCount;
    private Long price;
    @Convert(converter = ImageIdsConverter.class)
    private List<Long> imageIds;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
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
