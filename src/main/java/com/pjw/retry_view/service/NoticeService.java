package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.NoticeDTO;
import com.pjw.retry_view.entity.Notice;
import com.pjw.retry_view.entity.NoticeImage;
import com.pjw.retry_view.repository.NoticeRepository;
import com.pjw.retry_view.request.WriteNoticeRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<NoticeDTO> getNoticeList(){
        return noticeRepository.findAll().stream().map(Notice::toDTO).toList();
    }

    public NoticeDTO saveNotice(WriteNoticeRequest req){
        List<NoticeImage> images = req.getImages().stream().map(NoticeImage::getNoticeImage).toList();
        Notice notice = Notice.newOne(req.getContent(), req.getCreatedBy(), images);
        return noticeRepository.save(notice).toDTO();
    }

}
