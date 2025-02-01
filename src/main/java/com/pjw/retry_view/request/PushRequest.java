package com.pjw.retry_view.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class PushRequest {
    @NotEmpty(message = "제목은 필수 입력값입니다.")
    private String title;
    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String body;
    @NotNull(message = "유저 ID는 필수 입력값입니다.")
    private Set<Long> userIds;
}
