package com.pjw.retry_view.request;

import com.pjw.retry_view.enums.DeviceType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDeviceRequest {
    @NotNull(message = "디바이스 타입은 필수 입력값입니다.")
    private DeviceType type;
    @NotEmpty(message = "토큰은 필수 입력값입니다.")
    private String token;
}
