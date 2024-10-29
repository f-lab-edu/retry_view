package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.NoticeDTO;
import com.pjw.retry_view.entity.EventImage;
import com.pjw.retry_view.entity.Notice;
import com.pjw.retry_view.entity.NoticeImage;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repository.NoticeImageRepository;
import com.pjw.retry_view.repository.NoticeRepository;
import com.pjw.retry_view.request.WriteEventRequest;
import com.pjw.retry_view.request.WriteNoticeRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeImageRepository noticeImageRepository;

    public NoticeService(NoticeRepository noticeRepository, NoticeImageRepository noticeImageRepository) {
        this.noticeRepository = noticeRepository;
        this.noticeImageRepository = noticeImageRepository;
    }

    public List<NoticeDTO> getNoticeList(){
        return noticeRepository.findAll().stream().map(Notice::toDTO).toList();
    }

    public NoticeDTO getNotice(Long id){
        return noticeRepository.findById(id).orElseThrow(ResourceNotFoundException::new).toDTO();
    }

    @Transactional
    public NoticeDTO saveNotice(WriteNoticeRequest req){
        Notice notice = Notice.newOne(req.getContent(), req.getCreatedBy());
        List<NoticeImage> images = req.getImages().stream().map(img -> NoticeImage.newOne(null, img.getImageUrl(), req.getCreatedBy())).toList();
        noticeRepository.save(notice);

        for(NoticeImage noticeImage : images){
            noticeImage.changeNotice(notice);
            noticeImageRepository.save(noticeImage);
        }
        return notice.toDTO();
    }

    @Transactional
    public NoticeDTO updateNotice(WriteNoticeRequest req, Long id){
        Notice notice = noticeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<NoticeImage> reqImages = req.getImages().stream().map(img -> NoticeImage.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList();

        List<Long> imageIds = req.getImages().stream().map(WriteNoticeRequest.Image::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = notice.getNoticeImage().stream().map(NoticeImage::getId).toList();
        List<Long> deleteImageIds = getDeleteImageIds(imageIds, oldImageIds);
        if(!CollectionUtils.isEmpty(deleteImageIds)) {
            noticeImageRepository.deleteByIds(deleteImageIds);
            notice.getNoticeImage().removeIf(img->deleteImageIds.contains(img.getId()));
        }

        for(NoticeImage noticeImage : reqImages){
            if(noticeImage.getId() == null) {
                noticeImage.changeNotice(notice);
                noticeImageRepository.save(noticeImage);
            }
        }
        
        notice.updateNotice(req.getContent(), req.getUpdatedBy());
        return notice.toDTO();
    }

    @Transactional
    public void deleteNotice(Long id){
        Notice notice = noticeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        
    }

    public List<Long> getDeleteImageIds(List<Long> images, List<Long> oldImageIds){
        if(CollectionUtils.isEmpty(images) || CollectionUtils.isEmpty(oldImageIds)) return null;
        List<Long> deleteImageIds = new ArrayList<>();
        for(Long id : oldImageIds){
            if(!images.contains(id)){
                deleteImageIds.add(id);
            }
        }
        return deleteImageIds;
    }

}
