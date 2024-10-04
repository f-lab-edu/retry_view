package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.BoardImage;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardImageDTO {
    private Long id;
    private Long boardId;
    private String imageUrl;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    private BoardDTO board;

    public static BoardImageDTO getImageDTO(String imageUrl){
        return BoardImageDTO.builder().imageUrl(imageUrl).build();
    }

    public BoardImage toEntity(){
        return BoardImage.builder()
                .id(id)
                .imageUrl(imageUrl)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();
    }
}
