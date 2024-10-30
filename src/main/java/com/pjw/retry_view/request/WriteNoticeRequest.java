package com.pjw.retry_view.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WriteNoticeRequest {
    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String content;
    private List<ImageRequest> images;
    private Long createdBy;
    private Long updatedBy;
}
