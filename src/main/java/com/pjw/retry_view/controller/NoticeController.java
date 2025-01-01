package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.NoticeView;
import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.enums.ApiResponseCodeExamples;
import com.pjw.retry_view.enums.ErrorCode;
import com.pjw.retry_view.request.DeleteRequest;
import com.pjw.retry_view.request.WriteNoticeRequest;
import com.pjw.retry_view.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "공지사항 게시판 API 컨트롤러", description = "공지사항 게시글 API")
@RestController
@RequestMapping("/admin/notice")
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Operation(summary = "공지사항 게시글 목록 조회 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.INVALID_TOKEN})
    @GetMapping
    public List<NoticeView> getNoticeList(@RequestParam(name = "cursor", required = false) Long cursor){
        return noticeService.getNoticeList(cursor);
    }

    @Operation(summary = "공지사항 게시글 상세 조회 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.INVALID_TOKEN})
    @GetMapping("/{id}")
    public NoticeView getNotice(@PathVariable(name = "id") Long id){
        return noticeService.getNotice(id);
    }

    @Operation(summary = "공지사항 게시글 작성 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @PostMapping
    public NoticeView saveNotice(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteNoticeRequest notice){
        notice.setCreatedBy(userDetail.getId());
        return noticeService.saveNotice(notice);
    }

    @Operation(summary = "공지사항 게시글 수정 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.NOT_MY_RESOURCE, ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @PutMapping
    public NoticeView updateNotice(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteNoticeRequest notice){
        notice.setUpdatedBy(userDetail.getId());
        return noticeService.updateNotice(notice);
    }

    @Operation(summary = "공지사항 게시글 삭제 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.NOT_MY_RESOURCE, ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @DeleteMapping
    public void deleteNotice(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid DeleteRequest req){
        noticeService.deleteNotice(req.getId(), userDetail.getId());
    }
}
