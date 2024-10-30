package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.EventDTO;
import com.pjw.retry_view.dto.ImageType;
import com.pjw.retry_view.entity.Event;
import com.pjw.retry_view.entity.Image;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repository.ImageRepository;
import com.pjw.retry_view.repository.EventRepository;
import com.pjw.retry_view.request.ImageRequest;
import com.pjw.retry_view.request.WriteEventRequest;
import com.pjw.retry_view.util.Utils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final ImageRepository imageRepository;

    public EventService(EventRepository eventRepository, ImageRepository imageRepository) {
        this.eventRepository = eventRepository;
        this.imageRepository = imageRepository;
    }

    public List<EventDTO> getEventList(){
        List<Event> eventList = eventRepository.findAll();
        for(Event event: eventList){
            List<Image> imageList = imageRepository.findByTypeAndParentId(ImageType.EVENT, event.getId());
            event.setImages(imageList);
        }
        return eventList.stream().map(Event::toDTO).toList();
    }

    public EventDTO getEvent(Long id){
        Event event = eventRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> imageList = imageRepository.findByTypeAndParentId(ImageType.EVENT, event.getId());
        event.setImages(imageList);
        return event.toDTO();
    }

    @Transactional
    public EventDTO saveEvent(WriteEventRequest req){
        Event event = Event.newOne(req.getContent(), req.getStartAt(), req.getEndAt(), req.getCreatedBy());
        eventRepository.save(event);
        List<Image> images = req.getImages().stream().map(img -> Image.newOne(img.getId(), ImageType.EVENT, event.getId(), img.getImageUrl(), req.getCreatedBy())).toList();

        for(Image eventImage : images){
            eventImage.changeParentId(event.getId());
            imageRepository.save(eventImage);
        }
        return event.toDTO();
    }

    @Transactional
    public EventDTO updateEvent(WriteEventRequest req, Long id){
        Event event = eventRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> reqImages = req.getImages().stream().map(img -> Image.newOne(img.getId(), ImageType.EVENT, event.getId(), img.getImageUrl(), req.getCreatedBy())).toList();

        List<Long> imageIds = req.getImages().stream().map(ImageRequest::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = imageRepository.findByTypeAndParentId(ImageType.EVENT, id).stream().map(Image::getId).toList();
        List<Long> deleteImageIds = Utils.getDeleteImageIds(imageIds, oldImageIds);
        if(!CollectionUtils.isEmpty(deleteImageIds)) {
            imageRepository.deleteByIds(deleteImageIds);
            event.getImages().removeIf(img->deleteImageIds.contains(img.getId()));
        }

        for(Image eventImage : reqImages){
            if(eventImage.getId() == null) {
                eventImage.changeParentId(event.getId());
                imageRepository.save(eventImage);
            }
        }

        event.updateEvent(req.getContent(), req.getStartAt(), req.getEndAt(), req.getUpdatedBy());
        return eventRepository.save(event).toDTO();
    }

    @Transactional
    public void deleteEvent(Long id){
        Event event = eventRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        imageRepository.deleteByIds(event.getImages().stream().map(Image::getId).toList());
        eventRepository.deleteById(id);
    }

}
