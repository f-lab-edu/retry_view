package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.dto.ImageDTO;
import com.pjw.retry_view.entity.Board;
import com.pjw.retry_view.entity.Image;
import com.pjw.retry_view.enums.BoardType;
import com.pjw.retry_view.enums.SearchType;
import com.pjw.retry_view.exception.NotMyResourceException;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repository.BoardRepository;
import com.pjw.retry_view.repository.ImageRepository;
import com.pjw.retry_view.request.ImageRequest;
import com.pjw.retry_view.request.WriteBoardRequest;
import com.pjw.retry_view.util.Utils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private static final int DEFAULT_PAGE_SIZE = 3;

    public BoardService(BoardRepository boardRepository, ImageRepository imageRepository){
        this.boardRepository = boardRepository;
        this.imageRepository = imageRepository;
    }

    public List<BoardDTO> getBoardList(Long cursor, SearchType searchType, String content){
        Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

        List<Board> boardList = switch (SearchType.getValue(String.valueOf(searchType))) {
            case TITLE -> {
                content = "%" + content + "%";
                yield boardRepository.findByIdLessThanAndTitleLikeOrderByIdDesc(cursor, content, pageable);
            }
            case TYPE ->
                    boardRepository.findByIdLessThanAndTypeOrderByIdDesc(cursor, BoardType.getValue(content), pageable);
            default -> boardRepository.findAllByOrderByIdDesc(pageable);
        };

        for(Board board : boardList){
            if(CollectionUtils.isEmpty(board.getImageIds())) continue;

            List<Image> imageList = imageRepository.findByIdIn(board.getImageIds());
            board.setImages(imageList);
        }
        return boardList.stream().map(Board::toDTO).toList();
    }

    public BoardDTO getBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> imageList = imageRepository.findByIdIn(board.getImageIds());
        board.setImages(imageList);
        return board.toDTO();
    }

    @Transactional
    public BoardDTO saveBoard(WriteBoardRequest req){
        if(CollectionUtils.isEmpty(req.getImages())) {
            req.setImages(new ArrayList<>());
        }
        List<Image> images = req.getImages().stream().map(img -> Image.newOne(null, img.getImageUrl(), req.getCreatedBy())).toList();
        for(Image boardImage : images){
            imageRepository.save(boardImage);
        }

        List<Long> imageIds = images.stream().map(Image::getId).toList();
        Board board = Board.newOne(req.getType(), req.getProductId(), req.getTitle(), req.getContent(), req.getPrice(), imageIds, req.getCreatedBy());
        BoardDTO result = boardRepository.save(board).toDTO();
        result.setImages(images.stream().map(ImageDTO::fromEntity).toList());
        return result;
    }

    @Transactional
    public BoardDTO updateBoard(WriteBoardRequest req){
        Board board = boardRepository.findById(req.getId()).orElseThrow(ResolutionException::new);

        if(CollectionUtils.isEmpty(req.getImages())) {
            req.setImages(new ArrayList<>());
        }
        List<Image> reqImages = new ArrayList<>(req.getImages().stream().map(img -> Image.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList());

        if(Long.compare(board.getCreatedBy(), req.getUpdatedBy()) != 0)
            throw new NotMyResourceException("접근 불가능한 리소스입니다.");

        List<Long> imageIds = req.getImages().stream().map(ImageRequest::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = imageRepository.findByIdIn(board.getImageIds()).stream().map(Image::getId).toList();
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
        board.updateBoard(req.getId(), req.getType(), req.getProductId(), req.getTitle(), req.getContent(), req.getPrice(), updateImageIds, req.getUpdatedBy());
        BoardDTO result = boardRepository.save(board).toDTO();
        result.setImages(reqImages.stream().map(ImageDTO::fromEntity).toList());
        return result;
    }

    @Transactional
    public void deleteBoard(Long id, Long userId){
        Board board = boardRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        if(Long.compare(board.getCreatedBy(), userId) != 0)
            throw new NotMyResourceException("접근 불가능한 리소스입니다.");

        if(CollectionUtils.isEmpty(board.getImageIds())) imageRepository.deleteByIds(board.getImageIds());
        boardRepository.deleteById(id);
    }

}
