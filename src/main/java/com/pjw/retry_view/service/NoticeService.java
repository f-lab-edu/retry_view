package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.ImageType;
import com.pjw.retry_view.dto.NoticeDTO;
import com.pjw.retry_view.entity.Event;
import com.pjw.retry_view.entity.Image;
import com.pjw.retry_view.entity.Notice;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repository.ImageRepository;
import com.pjw.retry_view.repository.NoticeRepository;
import com.pjw.retry_view.request.ImageRequest;
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
    private final ImageRepository imageRepository;

    public NoticeService(NoticeRepository noticeRepository, ImageRepository imageRepository) {
        this.noticeRepository = noticeRepository;
        this.imageRepository = imageRepository;
    }

    public List<NoticeDTO> getNoticeList(){
        List<Notice> noticeList = noticeRepository.findAll();
        for(Notice notice: noticeList){
            List<Image> imageList = imageRepository.findByTypeAndParentId(ImageType.NOTICE, notice.getId());
            notice.setImages(imageList);
        }
        return noticeList.stream().map(Notice::toDTO).toList();
    }

    public NoticeDTO getNotice(Long id){
        Notice notice = noticeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> imageList = imageRepository.findByTypeAndParentId(ImageType.NOTICE, notice.getId());
        notice.setImages(imageList);
        return notice.toDTO();
    }

    @Transactional
    public NoticeDTO saveNotice(WriteNoticeRequest req){
        Notice notice = Notice.newOne(req.getContent(), req.getCreatedBy());
        noticeRepository.save(notice);
        List<Image> images = req.getImages().stream().map(img -> Image.newOne(null, ImageType.NOTICE, notice.getId(),  img.getImageUrl(), req.getCreatedBy())).toList();

        for(Image noticeImage : images){
            noticeImage.changeParentId(notice.getId());
            imageRepository.save(noticeImage);
        }
        return notice.toDTO();
    }

    @Transactional
    public NoticeDTO updateNotice(WriteNoticeRequest req, Long id){
        Notice notice = noticeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> reqImages = req.getImages().stream().map(img -> Image.newOne(img.getId(), ImageType.NOTICE, notice.getId(), img.getImageUrl(), req.getCreatedBy())).toList();

        List<Long> imageIds = req.getImages().stream().map(ImageRequest::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = imageRepository.findByTypeAndParentId(ImageType.EVENT, id).stream().map(Image::getId).toList();
        List<Long> deleteImageIds = getDeleteImageIds(imageIds, oldImageIds);
        if(!CollectionUtils.isEmpty(deleteImageIds)) {
            imageRepository.deleteByIds(deleteImageIds);
            //notice.getImages().removeIf(img->deleteImageIds.contains(img.getId()));
        }

        for(Image noticeImage : reqImages){
            if(noticeImage.getId() == null) {
                noticeImage.changeParentId(notice.getId());
                imageRepository.save(noticeImage);
            }
        }
        
        notice.updateNotice(req.getContent(), req.getUpdatedBy());
        return notice.toDTO();
    }

    @Transactional
    public void deleteNotice(Long id){
        Notice notice = noticeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> images = imageRepository.findByTypeAndParentId(ImageType.NOTICE, id);
        imageRepository.deleteByIds(notice.getImages().stream().map(Image::getId).toList());
        noticeRepository.deleteById(id);
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
