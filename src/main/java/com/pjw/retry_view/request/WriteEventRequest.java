package com.pjw.retry_view.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class WriteEventRequest implements Serializable {
    @NotEmpty(message = "제목은 필수 입력값입니다.")
    private String title;
    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String content;
    private List<ImageRequest> images;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private ZonedDateTime startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private ZonedDateTime endAt;
    private Long createdBy;
    private Long updatedBy;
}
