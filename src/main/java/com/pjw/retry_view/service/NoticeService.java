package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.ImageDTO;
import com.pjw.retry_view.dto.NoticeDTO;
import com.pjw.retry_view.entity.Image;
import com.pjw.retry_view.entity.Notice;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repository.ImageRepository;
import com.pjw.retry_view.repository.NoticeRepository;
import com.pjw.retry_view.request.ImageRequest;
import com.pjw.retry_view.request.WriteNoticeRequest;
import com.pjw.retry_view.util.Utils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final ImageRepository imageRepository;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public NoticeService(NoticeRepository noticeRepository, ImageRepository imageRepository) {
        this.noticeRepository = noticeRepository;
        this.imageRepository = imageRepository;
    }

    public List<NoticeDTO> getNoticeList(Long cursor){
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        List<Notice> noticeList = null;
        if(!Objects.isNull(cursor) && cursor > 0L){
            noticeList = noticeRepository.findByIdLessThanOrderByIdDesc(cursor, pageable);
        }else{
            noticeList = noticeRepository.findAllOrderByIdDesc(pageable);
        }

        for(Notice notice: noticeList){
            if(CollectionUtils.isEmpty(notice.getImageIds())) continue;

            List<Image> imageList = imageRepository.findByIds(notice.getImageIds());
            notice.setImages(imageList);
        }
        return noticeList.stream().map(Notice::toDTO).toList();
    }

    public NoticeDTO getNotice(Long id){
        Notice notice = noticeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> imageList = imageRepository.findByIds(notice.getImageIds());
        notice.setImages(imageList);
        return notice.toDTO();
    }

    @Transactional
    public NoticeDTO saveNotice(WriteNoticeRequest req){
        List<Image> images = req.getImages().stream().map(img -> Image.newOne(null, img.getImageUrl(), req.getCreatedBy())).toList();
        for(Image noticeImage : images){
            imageRepository.save(noticeImage);
        }

        List<Long> imageIds = images.stream().map(Image::getId).toList();
        Notice notice = Notice.newOne(req.getContent(), imageIds, req.getCreatedBy());
        NoticeDTO result = noticeRepository.save(notice).toDTO();
        result.setImages(images.stream().map(ImageDTO::fromEntity).toList());
        return result;
    }

    @Transactional
    public NoticeDTO updateNotice(WriteNoticeRequest req, Long id){
        Notice notice = noticeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> reqImages = new ArrayList<>(req.getImages().stream().map(img -> Image.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList());

        List<Long> imageIds = req.getImages().stream().map(ImageRequest::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = imageRepository.findByIds(notice.getImageIds()).stream().map(Image::getId).toList();
        List<Long> deleteImageIds = Utils.getDeleteImageIds(imageIds, oldImageIds);
        if(!CollectionUtils.isEmpty(deleteImageIds)) {
            imageRepository.deleteByIds(deleteImageIds);
            reqImages.removeIf(img->deleteImageIds.contains(img.getId()));
        }

        for(Image noticeImage : reqImages){
            if(noticeImage.getId() == null) {
                imageRepository.save(noticeImage);
            }
        }

        List<Long> updateImageIds = reqImages.stream().map(Image::getId).toList();
        notice.updateNotice(req.getContent(), updateImageIds, req.getUpdatedBy());
        NoticeDTO result = noticeRepository.save(notice).toDTO();
        result.setImages(reqImages.stream().map(ImageDTO::fromEntity).toList());
        return result;
    }

    @Transactional
    public void deleteNotice(Long id){
        Notice notice = noticeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if(CollectionUtils.isEmpty(notice.getImageIds())) imageRepository.deleteByIds(notice.getImageIds());
        noticeRepository.deleteById(id);
    }

}
