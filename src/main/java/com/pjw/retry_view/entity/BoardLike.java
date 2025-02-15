package com.pjw.retry_view.entity;

import com.pjw.retry_view.dto.BoardLikeView;
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
@Table(name = "board_like")
public class BoardLike {
    @EmbeddedId
    private LikeId id;

    private ZonedDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("boardId")
    private Board board;

    public static BoardLike newOne(Long userId, Long boardId){
        return BoardLike.builder()
                .userId(userId)
                .boardId(boardId)
                .createdAt(ZonedDateTime.now())
                .board(Board.builder().id(boardId).build())
                .build();
    }

    @Builder
    public BoardLike(Long userId, Long boardId, ZonedDateTime createdAt, Board board) {
        this.id = LikeId.newOne(userId, boardId);
        this.createdAt = createdAt;
        this.board = board;
    }

    public BoardLikeView toDTO(){
        return BoardLikeView.builder()
                .board(board.toDTO())
                .userId(id.getUserId())
                .createdAt(createdAt)
                .build();
    }
}
