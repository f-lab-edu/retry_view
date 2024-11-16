package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.NoticeDTO;
import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.request.WriteNoticeRequest;
import com.pjw.retry_view.service.NoticeService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/notice")
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public List<NoticeDTO> getNoticeList(@RequestParam(name = "cursor", required = false) Long cursor){
        return noticeService.getNoticeList(cursor);
    }

    @GetMapping("/{id}")
    public NoticeDTO getNotice(@PathVariable(name = "id") Long id){
        return noticeService.getNotice(id);
    }

    @PostMapping
    public NoticeDTO saveNotice(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteNoticeRequest notice){
        notice.setCreatedBy(userDetail.getId());
        return noticeService.saveNotice(notice);
    }

    @PutMapping("/{id}")
    public NoticeDTO updateNotice(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteNoticeRequest notice, @PathVariable(name = "id") Long id){
        notice.setUpdatedBy(userDetail.getId());
        return noticeService.updateNotice(notice, id);
    }

    @DeleteMapping("/{id}")
    public void deleteNotice(@PathVariable(name = "id") Long id){
        noticeService.deleteNotice(id);
    }
}
