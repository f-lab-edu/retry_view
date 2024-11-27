package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.NoticeDTO;
import com.pjw.retry_view.dto.UserDetail;
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
    @GetMapping
    public List<NoticeDTO> getNoticeList(@RequestParam(name = "cursor", required = false) Long cursor){
        return noticeService.getNoticeList(cursor);
    }

    @Operation(summary = "공지사항 게시글 상세 조회 API", description = "")
    @GetMapping("/{id}")
    public NoticeDTO getNotice(@PathVariable(name = "id") Long id){
        return noticeService.getNotice(id);
    }

    @Operation(summary = "공지사항 게시글 작성 API", description = "")
    @PostMapping
    public NoticeDTO saveNotice(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteNoticeRequest notice){
        notice.setCreatedBy(userDetail.getId());
        return noticeService.saveNotice(notice);
    }

    @Operation(summary = "공지사항 게시글 수정 API", description = "")
    @PutMapping("/{id}")
    public NoticeDTO updateNotice(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteNoticeRequest notice, @PathVariable(name = "id") Long id){
        notice.setUpdatedBy(userDetail.getId());
        return noticeService.updateNotice(notice, id);
    }

    @Operation(summary = "공지사항 게시글 삭제 API", description = "")
    @DeleteMapping("/{id}")
    public void deleteNotice(@PathVariable(name = "id") Long id){
        noticeService.deleteNotice(id);
    }
}
