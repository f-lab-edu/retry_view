package com.pjw.retry_view.request;

import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.dto.BoardImageDTO;
import com.pjw.retry_view.dto.BoardType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

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

    private List<BoardImageDTO> imagesToDTO(){
        if(CollectionUtils.isEmpty(images)) return null;
        return images.stream().map(BoardImageDTO::getImageDTO).toList();
    }
}