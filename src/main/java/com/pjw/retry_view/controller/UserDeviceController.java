package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.enums.ApiResponseCodeExamples;
import com.pjw.retry_view.enums.ErrorCode;
import com.pjw.retry_view.request.UserDeviceRequest;
import com.pjw.retry_view.service.UserDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserDevice API 컨트롤러", description = "유저의 디바이스 정보(토큰) 관리 API")
@RestController
@RequestMapping("/device")
public class UserDeviceController {
    private final UserDeviceService userDeviceService;

    public UserDeviceController(UserDeviceService userDeviceService) {
        this.userDeviceService = userDeviceService;
    }

    @Operation(summary = "유저 디바이스 정보 등록 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.DUPLICATE_REQ})
    @PostMapping
    public void saveDeviceInfo(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid UserDeviceRequest req){
        userDeviceService.saveUserDevice(req, userDetail.getId());
    }
}
