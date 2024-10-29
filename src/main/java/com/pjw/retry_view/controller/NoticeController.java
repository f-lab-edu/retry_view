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

    @GetMapping("/{id}")
    public NoticeDTO getNotice(@PathVariable(name = "id") Long id){
        return noticeService.getNotice(id);
    }

    @PostMapping
    public NoticeDTO saveNotice(@RequestBody @Valid WriteNoticeRequest notice){
        return noticeService.saveNotice(notice);
    }

    @PutMapping("/{id}")
    public NoticeDTO updateNotice(@RequestBody @Valid WriteNoticeRequest notice, @PathVariable(name = "id") Long id){
        return noticeService.updateNotice(notice, id);
    }

    @DeleteMapping("/{id}")
    public void deleteNotice(@PathVariable(name = "id") Long id){
        noticeService.deleteNotice(id);
    }
}
