package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.EventDTO;
import com.pjw.retry_view.dto.ImageDTO;
import com.pjw.retry_view.entity.Event;
import com.pjw.retry_view.entity.Image;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repository.ImageRepository;
import com.pjw.retry_view.repository.EventRepository;
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
    private final EventRepository eventRepository;
    private final ImageRepository imageRepository;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public EventService(EventRepository eventRepository, ImageRepository imageRepository) {
        this.eventRepository = eventRepository;
        this.imageRepository = imageRepository;
    }

    public List<EventDTO> getEventList(Long cursor){
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        List<Event> eventList = null;
        if(!Objects.isNull(cursor) && cursor > 0L){
            eventList = eventRepository.findByIdLessThanOrderByIdDesc(cursor, pageable);
        }else{
            eventList = eventRepository.findAllOrderByIdDesc(pageable);
        }

        for(Event event: eventList){
            if(CollectionUtils.isEmpty(event.getImageIds())) continue;

            List<Image> imageList = imageRepository.findByIds(event.getImageIds());
            event.setImages(imageList);
        }
        return eventList.stream().map(Event::toDTO).toList();
    }

    public EventDTO getEvent(Long id){
        Event event = eventRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> imageList = imageRepository.findByIds(event.getImageIds());
        event.setImages(imageList);
        return event.toDTO();
    }

    @Transactional
    public EventDTO saveEvent(WriteEventRequest req){
        List<Image> images = req.getImages().stream().map(img -> Image.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList();

        for(Image eventImage : images){
            imageRepository.save(eventImage);
        }

        List<Long> imageIds = images.stream().map(Image::getId).toList();
        Event event = Event.newOne(req.getContent(), imageIds, req.getStartAt(), req.getEndAt(), req.getCreatedBy());
        EventDTO result = eventRepository.save(event).toDTO();
        result.setImages(images.stream().map(ImageDTO::fromEntity).toList());
        return result;
    }

    @Transactional
    public EventDTO updateEvent(WriteEventRequest req, Long id){
        Event event = eventRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> reqImages = new ArrayList<>(req.getImages().stream().map(img -> Image.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList());

        List<Long> imageIds = req.getImages().stream().map(ImageRequest::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = imageRepository.findByIds(event.getImageIds()).stream().map(Image::getId).toList();
        List<Long> deleteImageIds = Utils.getDeleteImageIds(imageIds, oldImageIds);
        if(!CollectionUtils.isEmpty(deleteImageIds)) {
            imageRepository.deleteByIds(deleteImageIds);
            reqImages.removeIf(img->deleteImageIds.contains(img.getId()));
        }

        for(Image eventImage : reqImages){
            if(eventImage.getId() == null) {
                imageRepository.save(eventImage);
            }
        }

        List<Long> updateImageIds = reqImages.stream().map(Image::getId).toList();
        event.updateEvent(req.getContent(), updateImageIds, req.getStartAt(), req.getEndAt(), req.getUpdatedBy());
        EventDTO result = eventRepository.save(event).toDTO();
        result.setImages(reqImages.stream().map(ImageDTO::fromEntity).toList());
        return result;
    }

    @Transactional
    public void deleteEvent(Long id){
        Event event = eventRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if(CollectionUtils.isEmpty(event.getImageIds())) imageRepository.deleteByIds(event.getImageIds());
        eventRepository.deleteById(id);
    }

}
