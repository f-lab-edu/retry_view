package com.pjw.retry_view.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardLikeView {
    private Long userId;
    private BoardView board;
    private ZonedDateTime createdAt;

    @Builder
    public BoardLikeView(Long userId, BoardView board, ZonedDateTime createdAt) {
        this.userId = userId;
        this.board = board;
        this.createdAt = createdAt;
    }


}
