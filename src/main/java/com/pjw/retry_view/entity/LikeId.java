package com.pjw.retry_view.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class LikeId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "board_id")
    private Long boardId;

    public static LikeId newOne(Long userId, Long boardId){
        return LikeId.builder()
                .userId(userId)
                .boardId(boardId)
                .build();
    }

    @Builder
    public LikeId(Long userId, Long boardId) {
        this.userId = userId;
        this.boardId = boardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeId likeId = (LikeId) o;
        return Objects.equals(userId, likeId.userId) && Objects.equals(boardId, likeId.boardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, boardId);
    }
}
