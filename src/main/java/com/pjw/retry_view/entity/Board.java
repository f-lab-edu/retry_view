package com.pjw.retry_view.entity;

import com.pjw.retry_view.converter.BoardTypeEnumConverter;
import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.dto.BoardImageDTO;
import com.pjw.retry_view.dto.BoardType;
import com.pjw.retry_view.request.WriteBoardRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    @Column(name = "content")
    private String content;
    @Column(name = "view_count")
    @ColumnDefault("0")
    private Long viewCount;
    @Column(name = "price")
    private Long price;
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
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

    @Builder
    public Board(Long id, BoardType type, Long productId, String content, Long viewCount, Long price, List<BoardImage> boardImage, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.type = type;
        this.productId = productId;
        this.content = content;
        this.viewCount = viewCount;
        this.price = price;
        this.boardImage = boardImage;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public static Board newOne(BoardType type, Long productId, String content, Long price, Long createdBy){
        return Board.builder()
                .type(type)
                .productId(productId)
                .content(content)
                .viewCount(0L)
                .price(price)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    public void updateBoard(Long id, BoardType type, Long productId, String content, Long price, Long updatedBy){
        this.id = id;
        this.type = type;
        this.productId = productId;
        this.content = content;
        this.price = price;
        this.updatedBy = updatedBy;
        this.updatedAt = ZonedDateTime.now();
    }

    public void changeBoardImage(List<BoardImage> images){
        this.boardImage = images;
    }

    private List<BoardImageDTO> imagesToDTO(){
        if(CollectionUtils.isEmpty(boardImage)) return null;
        return boardImage.stream().map(BoardImage::toDTO).toList();
    }
}
