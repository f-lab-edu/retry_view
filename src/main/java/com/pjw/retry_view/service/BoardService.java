package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.BoardView;
import com.pjw.retry_view.dto.ImageView;
import com.pjw.retry_view.entity.Board;
import com.pjw.retry_view.entity.Image;
import com.pjw.retry_view.enums.BoardType;
import com.pjw.retry_view.enums.SearchType;
import com.pjw.retry_view.exception.NotMyResourceException;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repositoryImpl.BoardRepositoryImpl;
import com.pjw.retry_view.repositoryImpl.ImageRepositoryImpl;
import com.pjw.retry_view.request.ImageRequest;
import com.pjw.retry_view.request.WriteBoardRequest;
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
public class BoardService {
    private final BoardRepositoryImpl boardRepositoryImpl;
    private final ImageRepositoryImpl imageRepositoryImpl;
    private static final int DEFAULT_PAGE_SIZE = 3;

    public BoardService(BoardRepositoryImpl boardRepositoryImpl, ImageRepositoryImpl imageRepositoryImpl){
        this.boardRepositoryImpl = boardRepositoryImpl;
        this.imageRepositoryImpl = imageRepositoryImpl;
    }

    public List<BoardView> getBoardList(Long cursor, SearchType searchType, String content){
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);
        if(Objects.isNull(searchType)) searchType = SearchType.ALL;

        List<Board> boardList = switch (searchType) {
            case TITLE ->
                boardRepositoryImpl.findByIdLessThanAndTitleLikeOrderByIdDesc(cursor, "%" + content + "%", pageable);
            case TYPE ->
                    boardRepositoryImpl.findByIdLessThanAndTypeOrderByIdDesc(cursor, BoardType.getValue(content), pageable);
            default -> boardRepositoryImpl.findAllByOrderByIdDesc(pageable);
        };

        List<BoardView> dtoList = new ArrayList<>();
        for(Board board : boardList){
            if(CollectionUtils.isEmpty(board.getImageIds())) continue;

            List<Image> imageList = imageRepositoryImpl.findByIdIn(board.getImageIds());
            dtoList.add(BoardView.from(board, imageList));
        }
        return dtoList;
    }

    public BoardView getBoard(Long id){
        Board board = boardRepositoryImpl.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> imageList = imageRepositoryImpl.findByIdIn(board.getImageIds());
        return BoardView.from(board, imageList);
    }

    @Transactional
    public BoardView saveBoard(WriteBoardRequest req){
        if(CollectionUtils.isEmpty(req.getImages())) {
            req.setImages(new ArrayList<>());
        }
        List<Image> images = req.getImages().stream().map(img -> Image.newOne(null, img.getImageUrl(), req.getCreatedBy())).toList();
        for(Image boardImage : images){
            imageRepositoryImpl.save(boardImage);
        }

        List<Long> imageIds = images.stream().map(Image::getId).toList();
        Board board = Board.newOne(req.getType(), req.getProductId(), req.getTitle(), req.getContent(), req.getPrice(), imageIds, req.getCreatedBy());
        BoardView result = boardRepositoryImpl.save(board).toDTO();
        result.setImages(images.stream().map(ImageView::fromEntity).toList());
        return result;
    }

    @Transactional
    public BoardView updateBoard(WriteBoardRequest req){
        Board board = boardRepositoryImpl.findById(req.getId()).orElseThrow(ResourceNotFoundException::new);

        if(CollectionUtils.isEmpty(req.getImages())) {
            req.setImages(new ArrayList<>());
        }
        List<Image> reqImages = new ArrayList<>(req.getImages().stream().map(img -> Image.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList());

        if(Long.compare(board.getCreatedBy(), req.getUpdatedBy()) != 0)
            throw new NotMyResourceException("접근 불가능한 리소스입니다.");

        List<Long> imageIds = req.getImages().stream().map(ImageRequest::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = imageRepositoryImpl.findByIdIn(board.getImageIds()).stream().map(Image::getId).toList();
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
        board.updateBoard(req.getId(), req.getType(), req.getProductId(), req.getTitle(), req.getContent(), req.getPrice(), updateImageIds, req.getUpdatedBy());
        BoardView result = boardRepositoryImpl.save(board).toDTO();
        result.setImages(reqImages.stream().map(ImageView::fromEntity).toList());
        return result;
    }

    @Transactional
    public void deleteBoard(Long id, Long userId){
        Board board = boardRepositoryImpl.findById(id).orElseThrow(ResourceNotFoundException::new);

        if(Long.compare(board.getCreatedBy(), userId) != 0)
            throw new NotMyResourceException("접근 불가능한 리소스입니다.");

        if(CollectionUtils.isEmpty(board.getImageIds())) imageRepositoryImpl.deleteByIdIn(board.getImageIds());
        boardRepositoryImpl.deleteById(id);
    }

}
