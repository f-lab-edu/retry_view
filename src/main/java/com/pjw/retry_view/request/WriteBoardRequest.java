package com.pjw.retry_view.request;

import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.dto.BoardImageDTO;
import com.pjw.retry_view.dto.BoardType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class WriteBoardRequest implements Serializable {
    private Long id;
    private BoardType type;
    private Long productId;
    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String content;
    @NotNull(message = "가격은 필수 입력값입니다.")
    private Long price;
    private List<String> images;
    private Long createdBy;

    public BoardDTO toBoardDTO(){
        return BoardDTO.builder()
                .type(type)
                .productId(productId)
                .content(content)
                .viewCount(0L)
                .price(price)
                .images(imagesToDTO())
                .createdBy(createdBy)
                .build();
    }

    private List<BoardImageDTO> imagesToDTO(){
        if(images == null) return null;
        return images.stream().map(BoardImageDTO::getImageDTO).toList();
    }
}
