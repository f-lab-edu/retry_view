package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.ImageView;
import com.pjw.retry_view.dto.NoticeView;
import com.pjw.retry_view.entity.Image;
import com.pjw.retry_view.entity.Notice;
import com.pjw.retry_view.exception.NotMyResourceException;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repositoryImpl.ImageRepositoryImpl;
import com.pjw.retry_view.repositoryImpl.NoticeRepositoryImpl;
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
    private final NoticeRepositoryImpl noticeRepositoryImpl;
    private final ImageRepositoryImpl imageRepositoryImpl;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public NoticeService(NoticeRepositoryImpl noticeRepositoryImpl, ImageRepositoryImpl imageRepositoryImpl) {
        this.noticeRepositoryImpl = noticeRepositoryImpl;
        this.imageRepositoryImpl = imageRepositoryImpl;
    }

    public List<NoticeView> getNoticeList(Long cursor){
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        List<Notice> noticeList = null;
        if(!Objects.isNull(cursor) && cursor > 0L){
            noticeList = noticeRepositoryImpl.findByIdLessThanOrderByIdDesc(cursor, pageable);
        }else{
            noticeList = noticeRepositoryImpl.findAllByOrderByIdDesc(pageable);
        }

        List<NoticeView> dtoList = new ArrayList<>();
        for(Notice notice: noticeList){
            if(CollectionUtils.isEmpty(notice.getImageIds())) continue;

            List<Image> imageList = imageRepositoryImpl.findByIdIn(notice.getImageIds());
            dtoList.add(NoticeView.from(notice, imageList));
        }
        return dtoList;
    }

    public NoticeView getNotice(Long id){
        Notice notice = noticeRepositoryImpl.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> imageList = imageRepositoryImpl.findByIdIn(notice.getImageIds());
        return NoticeView.from(notice, imageList);
    }

    @Transactional
    public NoticeView saveNotice(WriteNoticeRequest req){
        if(CollectionUtils.isEmpty(req.getImages())) {
            req.setImages(new ArrayList<>());
        }
        List<Image> images = req.getImages().stream().map(img -> Image.newOne(null, img.getImageUrl(), req.getCreatedBy())).toList();
        for(Image noticeImage : images){
            imageRepositoryImpl.save(noticeImage);
        }

        List<Long> imageIds = images.stream().map(Image::getId).toList();
        Notice notice = Notice.newOne(req.getTitle(), req.getContent(), imageIds, req.getCreatedBy());
        NoticeView result = noticeRepositoryImpl.save(notice).toDTO();
        result.setImages(images.stream().map(ImageView::fromEntity).toList());
        return result;
    }

    @Transactional
    public NoticeView updateNotice(WriteNoticeRequest req){
        Notice notice = noticeRepositoryImpl.findById(req.getId()).orElseThrow(ResourceNotFoundException::new);

        if(Long.compare(notice.getCreatedBy(), req.getUpdatedBy()) != 0)
            throw new NotMyResourceException("접근 불가능한 리소스입니다.");

        if(CollectionUtils.isEmpty(req.getImages())) {
            req.setImages(new ArrayList<>());
        }
        List<Image> reqImages = new ArrayList<>(req.getImages().stream().map(img -> Image.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList());
        List<Long> imageIds = req.getImages().stream().map(ImageRequest::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = imageRepositoryImpl.findByIdIn(notice.getImageIds()).stream().map(Image::getId).toList();
        List<Long> deleteImageIds = Utils.getDeleteImageIds(imageIds, oldImageIds);
        if(!CollectionUtils.isEmpty(deleteImageIds)) {
            imageRepositoryImpl.deleteByIdIn(deleteImageIds);
            reqImages.removeIf(img->deleteImageIds.contains(img.getId()));
        }

        for(Image noticeImage : reqImages){
            if(noticeImage.getId() == null) {
                imageRepositoryImpl.save(noticeImage);
            }
        }

        List<Long> updateImageIds = reqImages.stream().map(Image::getId).toList();
        notice.updateNotice(req.getTitle(), req.getContent(), updateImageIds, req.getUpdatedBy());
        NoticeView result = noticeRepositoryImpl.save(notice).toDTO();
        result.setImages(reqImages.stream().map(ImageView::fromEntity).toList());
        return result;
    }

    @Transactional
    public void deleteNotice(Long id, Long userId){
        Notice notice = noticeRepositoryImpl.findById(id).orElseThrow(ResourceNotFoundException::new);

        if(Long.compare(notice.getCreatedBy(), userId) != 0)
            throw new NotMyResourceException("접근 불가능한 리소스입니다.");

        if(CollectionUtils.isEmpty(notice.getImageIds())) imageRepositoryImpl.deleteByIdIn(notice.getImageIds());
        noticeRepositoryImpl.deleteById(id);
    }

}
