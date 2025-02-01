package com.pjw.retry_view.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class LikedEvent {
    private Set<Long> boardIds;

    public static LikedEvent newOne(Set<Long> boardIds){
        return LikedEvent.builder()
                .boardIds(boardIds)
                .build();
    }

    @Builder
    public LikedEvent(Set<Long> boardIds) {
        this.boardIds = boardIds;
    }
}
