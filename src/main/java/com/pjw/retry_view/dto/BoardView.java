package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.Board;
import com.pjw.retry_view.entity.Image;
import com.pjw.retry_view.enums.BoardType;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardView {
    private Long id;
    private BoardType type;
    private Long productId;
    private String title;
    private String content;
    private Long viewCount;
    private Long price;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    private List<ImageView> images;

    public static BoardView from(Board board, List<Image> images){
        if(images == null) images = new ArrayList<>();

        return BoardView.builder()
                .id(board.getId())
                .type(board.getType())
                .productId(board.getProductId())
                .content(board.getContent())
                .viewCount(board.getViewCount())
                .price(board.getPrice())
                .images(images.stream().map(ImageView::fromEntity).toList())
                .createdBy(board.getCreatedBy())
                .createdAt(board.getCreatedAt())
                .updatedBy(board.getUpdatedBy())
                .updatedAt(board.getUpdatedAt())
                .build();
    }

    @Builder
    public BoardView(Long id, BoardType type, Long productId, String content, Long viewCount, Long price, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt, List<ImageView> images) {
        this.id = id;
        this.type = type;
        this.productId = productId;
        this.content = content;
        this.viewCount = viewCount;
        this.price = price;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.images = images;
    }

}
