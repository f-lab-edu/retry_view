package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.EventView;
import com.pjw.retry_view.dto.ImageView;
import com.pjw.retry_view.entity.Event;
import com.pjw.retry_view.entity.Image;
import com.pjw.retry_view.exception.NotMyResourceException;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repositoryImpl.ImageRepositoryImpl;
import com.pjw.retry_view.repositoryImpl.EventRepositoryImpl;
import com.pjw.retry_view.request.ImageRequest;
import com.pjw.retry_view.request.WriteEventRequest;
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
public class EventService {
    private final EventRepositoryImpl eventRepositoryImpl;
    private final ImageRepositoryImpl imageRepositoryImpl;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public EventService(EventRepositoryImpl eventRepositoryImpl, ImageRepositoryImpl imageRepositoryImpl) {
        this.eventRepositoryImpl = eventRepositoryImpl;
        this.imageRepositoryImpl = imageRepositoryImpl;
    }

    public List<EventView> getEventList(Long cursor){
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        List<Event> eventList = null;
        if(!Objects.isNull(cursor) && cursor > 0L){
            eventList = eventRepositoryImpl.findByIdLessThanOrderByIdDesc(cursor, pageable);
        }else{
            eventList = eventRepositoryImpl.findAllByOrderByIdDesc(pageable);
        }

        List<EventView> dtoList = new ArrayList<>();
        for(Event event: eventList){
            if(CollectionUtils.isEmpty(event.getImageIds())) continue;

            List<Image> imageList = imageRepositoryImpl.findByIdIn(event.getImageIds());
            dtoList.add(EventView.from(event, imageList));
        }
        return dtoList;
    }

    public EventView getEvent(Long id){
        Event event = eventRepositoryImpl.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> imageList = imageRepositoryImpl.findByIdIn(event.getImageIds());
        return EventView.from(event, imageList);
    }

    @Transactional
    public EventView saveEvent(WriteEventRequest req){
        List<Image> images = req.getImages().stream().map(img -> Image.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList();

        for(Image eventImage : images){
            imageRepositoryImpl.save(eventImage);
        }

        List<Long> imageIds = images.stream().map(Image::getId).toList();
        Event event = Event.newOne(req.getTitle(), req.getContent(), imageIds, req.getStartAt(), req.getEndAt(), req.getCreatedBy());
        EventView result = eventRepositoryImpl.save(event).toDTO();
        result.setImages(images.stream().map(ImageView::fromEntity).toList());
        return result;
    }

    @Transactional
    public EventView updateEvent(WriteEventRequest req){
        if(CollectionUtils.isEmpty(req.getImages())) {
            req.setImages(new ArrayList<>());
        }
        Event event = eventRepositoryImpl.findById(req.getId()).orElseThrow(ResourceNotFoundException::new);

        if(Long.compare(event.getCreatedBy(), req.getUpdatedBy()) != 0)
            throw new NotMyResourceException("접근 불가능한 리소스입니다.");

        List<Image> reqImages = new ArrayList<>(req.getImages().stream().map(img -> Image.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList());

        List<Long> imageIds = req.getImages().stream().map(ImageRequest::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = imageRepositoryImpl.findByIdIn(event.getImageIds()).stream().map(Image::getId).toList();
        List<Long> deleteImageIds = Utils.getDeleteImageIds(imageIds, oldImageIds);
        if(!CollectionUtils.isEmpty(deleteImageIds)) {
            imageRepositoryImpl.deleteByIdIn(deleteImageIds);
            reqImages.removeIf(img->deleteImageIds.contains(img.getId()));
        }

        for(Image eventImage : reqImages){
            if(eventImage.getId() == null) {
                imageRepositoryImpl.save(eventImage);
            }
        }

        List<Long> updateImageIds = reqImages.stream().map(Image::getId).toList();
        event.updateEvent(req.getTitle(), req.getContent(), updateImageIds, req.getStartAt(), req.getEndAt(), req.getUpdatedBy());
        EventView result = eventRepositoryImpl.save(event).toDTO();
        result.setImages(reqImages.stream().map(ImageView::fromEntity).toList());
        return result;
    }

    @Transactional
    public void deleteEvent(Long id, Long userId){
        Event event = eventRepositoryImpl.findById(id).orElseThrow(ResourceNotFoundException::new);

        if(Long.compare(event.getCreatedBy(), userId) != 0)
            throw new NotMyResourceException("접근 불가능한 리소스입니다.");

        if(CollectionUtils.isEmpty(event.getImageIds())) imageRepositoryImpl.deleteByIdIn(event.getImageIds());
        eventRepositoryImpl.deleteById(id);
    }

}
