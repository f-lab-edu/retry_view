package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.ImageDTO;
import com.pjw.retry_view.dto.Reviews;
import com.pjw.retry_view.entity.Image;
import com.pjw.retry_view.entity.Review;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repository.ImageRepository;
import com.pjw.retry_view.repository.ReviewRepository;
import com.pjw.retry_view.request.ImageRequest;
import com.pjw.retry_view.request.ReviewRequest;
import com.pjw.retry_view.util.Utils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private static final int DEFAULT_PAGE_SIZE = 3;

    public ReviewService(ReviewRepository reviewRepository, ImageRepository imageRepository) {
        this.reviewRepository = reviewRepository;
        this.imageRepository = imageRepository;
    }

    public List<Reviews> getReviewListByProductId(Long productId){
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        List<Review> reviewList = reviewRepository.findByProductId(productId, pageable);
        List<Reviews> result = new ArrayList<>();
        for(Review review : reviewList){
            if(CollectionUtils.isEmpty(review.getImageIds())) continue;

            List<Image> imageList = imageRepository.findByIds(review.getImageIds());
            Reviews item = Reviews.fromEntity(review);
            item.setImages(imageList.stream().map(ImageDTO::fromEntity).toList());

            result.add(item);
        }
        return result;
    }

    public List<Reviews> getReviewListByCreatedBy(Long createdBy){
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        List<Review> reviewList = reviewRepository.findByProductId(createdBy, pageable);
        List<Reviews> result = new ArrayList<>();
        for(Review review : reviewList){
            if(CollectionUtils.isEmpty(review.getImageIds())) continue;

            List<Image> imageList = imageRepository.findByIds(review.getImageIds());
            Reviews item = Reviews.fromEntity(review);
            item.setImages(imageList.stream().map(ImageDTO::fromEntity).toList());

            result.add(item);
        }
        return result;
    }

    public Reviews saveReview(ReviewRequest req){
        List<Image> images = req.getImages().stream().map(img -> Image.newOne(null, img.getImageUrl(), req.getCreatedBy())).toList();
        for(Image reviewImage : images){
            imageRepository.save(reviewImage);
        }

        List<Long> imageIds = images.stream().map(Image::getId).toList();
        Review review = Review.newOne(req.getProductId(), req.getScore(), req.getComment(), imageIds, req.getCreatedBy());
        Reviews result = Reviews.fromEntity(reviewRepository.save(review));
        result.setImages(images.stream().map(ImageDTO::fromEntity).toList());
        return result;
    }

    public Reviews updateReview(ReviewRequest req, Long id){
        Review review = reviewRepository.findById(id).orElseThrow(ResolutionException::new);
        List<Image> reqImages = new ArrayList<>(req.getImages().stream().map(img -> Image.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList());

        List<Long> imageIds = req.getImages().stream().map(ImageRequest::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = imageRepository.findByIds(review.getImageIds()).stream().map(Image::getId).toList();
        List<Long> deleteImageIds = Utils.getDeleteImageIds(imageIds, oldImageIds);
        if(!CollectionUtils.isEmpty(deleteImageIds)) {
            imageRepository.deleteByIds(deleteImageIds);
            reqImages.removeIf(img->deleteImageIds.contains(img.getId()));
        }

        for(Image boardImage : reqImages){
            if(boardImage.getId() == null) {
                imageRepository.save(boardImage);
            }
        }

        List<Long> updateImageIds = reqImages.stream().map(Image::getId).toList();
        review.updateReview(id, req.getProductId(), req.getScore(), req.getComment(), updateImageIds, req.getUpdatedBy());
        Reviews result = Reviews.fromEntity(reviewRepository.save(review));
        result.setImages(reqImages.stream().map(ImageDTO::fromEntity).toList());
        return result;
    }

    public void deleteById(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if(CollectionUtils.isEmpty(review.getImageIds())) imageRepository.deleteByIds(review.getImageIds());
        reviewRepository.deleteById(id);
    }
}
