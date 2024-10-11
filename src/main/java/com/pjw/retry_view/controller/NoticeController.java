package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.NoticeDTO;
import com.pjw.retry_view.request.WriteNoticeRequest;
import com.pjw.retry_view.service.NoticeService;
import jakarta.validation.Valid;
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
    public List<NoticeDTO> getNoticeList(){
        return noticeService.getNoticeList();
    }

    @PostMapping
    public NoticeDTO writeNotice(@RequestBody @Valid WriteNoticeRequest notice){
        return noticeService.saveNotice(notice);
    }
}
