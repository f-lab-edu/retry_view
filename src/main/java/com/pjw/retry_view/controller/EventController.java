package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.EventView;
import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.enums.ApiResponseCodeExamples;
import com.pjw.retry_view.enums.ErrorCode;
import com.pjw.retry_view.request.DeleteRequest;
import com.pjw.retry_view.request.WriteEventRequest;
import com.pjw.retry_view.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "이벤트 게시판 API 컨트롤러", description = "이벤트 게시글 API")
@RestController
@RequestMapping("/admin/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @Operation(summary = "이벤트 게시글 목록 조회 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.INVALID_TOKEN})
    @GetMapping
    public List<EventView> getEventList(@RequestParam(name = "cursor", required = false) Long cursor){
        return eventService.getEventList(cursor);
    }

    @Operation(summary = "이벤트 게시글 상세 조회 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.INVALID_TOKEN})
    @GetMapping("/{id}")
    public EventView getEvent(@PathVariable(name = "id")Long id){
        return eventService.getEvent(id);
    }

    @Operation(summary = "이벤트 게시글 작성 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @PostMapping
    public EventView writeEvent(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteEventRequest event){
        event.setCreatedBy(userDetail.getId());
        return eventService.saveEvent(event);
    }

    @Operation(summary = "이벤트 게시글 수정 조회 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @PutMapping
    public EventView updateEvent(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteEventRequest event){
        event.setUpdatedBy(userDetail.getId());
        return eventService.updateEvent(event);
    }

    @Operation(summary = "이벤트 게시글 삭제 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.NOT_MY_RESOURCE, ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @DeleteMapping
    public void deleteEvent(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid DeleteRequest req){
        eventService.deleteEvent(req.getId(), userDetail.getId());
    }
}
