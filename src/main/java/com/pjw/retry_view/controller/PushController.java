package com.pjw.retry_view.controller;

import com.pjw.retry_view.enums.ApiResponseCodeExamples;
import com.pjw.retry_view.enums.ErrorCode;
import com.pjw.retry_view.request.PushRequest;
import com.pjw.retry_view.service.PushService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Push API 컨트롤러", description = "Push API")
@RestController
@RequestMapping("/push")
public class PushController {
    private final PushService pushService;

    public PushController(PushService pushService) {
        this.pushService = pushService;
    }

    @Operation(summary = "Push 발송 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @PostMapping
    public void sendPush(@RequestBody PushRequest req){
        pushService.send(req);
    }
}
