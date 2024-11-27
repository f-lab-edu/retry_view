package com.pjw.retry_view.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReviewRequest {
    private Long id;
    @NotNull(message = "상품 정보는 필수 입력값입니다.")
    private Long productId;
    private Integer score;
    @NotEmpty(message = "리뷰 내용은 필수 입력값입니다.")
    private String comment;
    private List<ImageRequest> images;

    private Long createdBy;
    private Long updatedBy;
}
