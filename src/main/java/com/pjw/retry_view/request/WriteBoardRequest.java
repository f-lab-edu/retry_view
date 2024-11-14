package com.pjw.retry_view.request;

import com.pjw.retry_view.enums.BoardType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WriteBoardRequest {
    private BoardType type;
    private Long productId;
    @NotEmpty(message = "제목은 필수 입력값입니다.")
    private String title;
    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String content;
    @NotNull(message = "가격은 필수 입력값입니다.")
    private Long price;
    private List<ImageRequest> images;
    private Long createdBy;
    private Long updatedBy;
}
