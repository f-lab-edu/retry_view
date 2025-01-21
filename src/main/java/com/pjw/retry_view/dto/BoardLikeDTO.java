package com.pjw.retry_view.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardLikeDTO {
    private Long userId;
    private BoardDTO board;
    private ZonedDateTime createdAt;

    @Builder
    public BoardLikeDTO(Long userId, BoardDTO board, ZonedDateTime createdAt) {
        this.userId = userId;
        this.board = board;
        this.createdAt = createdAt;
    }


}
