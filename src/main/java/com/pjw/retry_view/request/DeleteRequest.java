package com.pjw.retry_view.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRequest {
    @NotNull(message = "id는 필수 입력값입니다.")
    private Long id;
}
