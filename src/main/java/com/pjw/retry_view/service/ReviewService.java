package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.ImageView;
import com.pjw.retry_view.dto.ReviewView;
import com.pjw.retry_view.entity.Image;
import com.pjw.retry_view.entity.Review;
import com.pjw.retry_view.exception.NotMyResourceException;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repositoryImpl.ImageRepositoryImpl;
import com.pjw.retry_view.repositoryImpl.ReviewRepositoryImpl;
import com.pjw.retry_view.request.ImageRequest;
import com.pjw.retry_view.request.ReviewRequest;
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
public class ReviewService {
    private final ReviewRepositoryImpl reviewRepositoryImpl;
    private final ImageRepositoryImpl imageRepositoryImpl;
    private static final int DEFAULT_PAGE_SIZE = 3;

    public ReviewService(ReviewRepositoryImpl reviewRepositoryImpl, ImageRepositoryImpl imageRepositoryImpl) {
        this.reviewRepositoryImpl = reviewRepositoryImpl;
        this.imageRepositoryImpl = imageRepositoryImpl;
    }

    public List<ReviewView> getReviewListByProductId(Long cursor, Long productId){
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        List<Review> reviewList = reviewRepositoryImpl.findByIdLessThanAndProductIdOrderByIdDesc(cursor, productId, pageable);
        List<ReviewView> result = new ArrayList<>();
        List<Long> imageIds = new ArrayList<>();
        reviewList.forEach(review -> imageIds.addAll(review.getImageIds()));
        List<Image> imageList = imageRepositoryImpl.findByIdIn(imageIds);
        for(Review review : reviewList){
            if(CollectionUtils.isEmpty(review.getImageIds())) continue;

            List<Image> reviewImage = imageList.stream().filter(img-> review.getImageIds().contains(img.getId())).toList();
            ReviewView reviewView = ReviewView.fromEntity(review);
            reviewView.setImages(reviewImage.stream().map(ImageView::fromEntity).toList());

            result.add(reviewView);
        }
        return result;
    }

    public List<ReviewView> getReviewListByCreatedBy(Long cursor, Long createdBy){
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        List<Review> reviewList = reviewRepositoryImpl.findByIdLessThanAndCreatedByOrderByIdDesc(cursor, createdBy, pageable);
        List<ReviewView> result = new ArrayList<>();
        List<Long> imageIds = new ArrayList<>();
        reviewList.forEach(review -> imageIds.addAll(review.getImageIds()));
        List<Image> imageList = imageRepositoryImpl.findByIdIn(imageIds);
        for(Review review : reviewList){
            if(CollectionUtils.isEmpty(review.getImageIds())) continue;

            List<Image> reviewImage = imageList.stream().filter(img-> review.getImageIds().contains(img.getId())).toList();
            ReviewView reviewView = ReviewView.fromEntity(review);
            reviewView.setImages(reviewImage.stream().map(ImageView::fromEntity).toList());

            result.add(reviewView);
        }
        return result;
    }

    @Transactional
    public ReviewView saveReview(ReviewRequest req){
        if(CollectionUtils.isEmpty(req.getImages())) {
            req.setImages(new ArrayList<>());
        }
        List<Image> images = req.getImages().stream().map(img -> Image.newOne(null, img.getImageUrl(), req.getCreatedBy())).toList();
        for(Image reviewImage : images){
            imageRepositoryImpl.save(reviewImage);
        }

        List<Long> imageIds = images.stream().map(Image::getId).toList();
        Review review = Review.newOne(req.getProductId(), req.getScore(), req.getComment(), imageIds, req.getCreatedBy());
        ReviewView result = ReviewView.fromEntity(reviewRepositoryImpl.save(review));
        result.setImages(images.stream().map(ImageView::fromEntity).toList());
        return result;
    }

    @Transactional
    public ReviewView updateReview(ReviewRequest req){
        Review review = reviewRepositoryImpl.findById(req.getId()).orElseThrow(ResourceNotFoundException::new);

        if(Long.compare(review.getCreatedBy(), req.getUpdatedBy()) != 0)
            throw new NotMyResourceException("접근 불가능한 리소스입니다.");

        if(CollectionUtils.isEmpty(req.getImages())) {
            req.setImages(new ArrayList<>());
        }
        List<Image> reqImages = new ArrayList<>(req.getImages().stream().map(img -> Image.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList());
        List<Long> imageIds = req.getImages().stream().map(ImageRequest::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = imageRepositoryImpl.findByIdIn(review.getImageIds()).stream().map(Image::getId).toList();
        List<Long> deleteImageIds = Utils.getDeleteImageIds(imageIds, oldImageIds);
        if(!CollectionUtils.isEmpty(deleteImageIds)) {
            imageRepositoryImpl.deleteByIdIn(deleteImageIds);
            reqImages.removeIf(img->deleteImageIds.contains(img.getId()));
        }

        for(Image boardImage : reqImages){
            if(boardImage.getId() == null) {
                imageRepositoryImpl.save(boardImage);
            }
        }

        List<Long> updateImageIds = reqImages.stream().map(Image::getId).toList();
        review.updateReview(req.getProductId(), req.getScore(), req.getComment(), updateImageIds, req.getUpdatedBy());
        ReviewView result = ReviewView.fromEntity(reviewRepositoryImpl.save(review));
        result.setImages(reqImages.stream().map(ImageView::fromEntity).toList());
        return result;
    }

    @Transactional
    public void deleteById(Long id, Long userId){
        Review review = reviewRepositoryImpl.findById(id).orElseThrow(ResourceNotFoundException::new);

        if(Long.compare(review.getCreatedBy(), userId) != 0)
            throw new NotMyResourceException("접근 불가능한 리소스입니다.");

        if(CollectionUtils.isEmpty(review.getImageIds())) imageRepositoryImpl.deleteByIdIn(review.getImageIds());
        reviewRepositoryImpl.deleteById(id);
    }
}
