package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.dto.ImageType;
import com.pjw.retry_view.entity.Board;
import com.pjw.retry_view.entity.Image;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repository.BoardRepository;
import com.pjw.retry_view.repository.ImageRepository;
import com.pjw.retry_view.request.ImageRequest;
import com.pjw.retry_view.request.WriteBoardRequest;
import jakarta.transaction.Transactional;
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

    public BoardService(BoardRepository boardRepository, ImageRepository imageRepository){
        this.boardRepository = boardRepository;
        this.imageRepository = imageRepository;
    }

    public List<BoardDTO> getBoardList(){
        List<Board> boardList = boardRepository.findAll();
        for(Board board : boardList){
            List<Image> imageList = imageRepository.findByTypeAndParentId(ImageType.BOARD, board.getId());
            board.setImages(imageList);
        }
        return boardList.stream().map(Board::toDTO).toList();
    }

    public BoardDTO getBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Image> imageList = imageRepository.findByTypeAndParentId(ImageType.BOARD, id);
        board.setImages(imageList);
        return board.toDTO();
    }

    @Transactional
    public BoardDTO saveBoard(WriteBoardRequest req){
        Board board = Board.newOne(req.getType(), req.getProductId(), req.getContent(), req.getPrice(), req.getCreatedBy());
        boardRepository.save(board);
        List<Image> images = req.getImages().stream().map(img -> Image.newOne(null, ImageType.BOARD, board.getId(),  img.getImageUrl(), req.getCreatedBy())).toList();

        for(Image boardImage : images){
            boardImage.changeParentId(board.getId());
            imageRepository.save(boardImage);
        }

        board.changeBoardImage(images);
        return board.toDTO();
    }

    @Transactional
    public BoardDTO updateBoard(WriteBoardRequest req, Long id){
        Board board = boardRepository.findById(id).orElseThrow(ResolutionException::new);
        List<Image> reqImages = req.getImages().stream().map(img -> Image.newOne(img.getId(), ImageType.BOARD, board.getId(), img.getImageUrl(), req.getCreatedBy())).toList();

        List<Long> imageIds = req.getImages().stream().map(ImageRequest::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = imageRepository.findByTypeAndParentId(ImageType.BOARD, id).stream().map(Image::getId).toList();
        List<Long> deleteImageIds = getDeleteImageIds(imageIds, oldImageIds);
        if(!CollectionUtils.isEmpty(deleteImageIds)) {
            imageRepository.deleteByIds(deleteImageIds);
            board.getImages().removeIf(img->deleteImageIds.contains(img.getId()));
        }

        for(Image boardImage : reqImages){
            if(boardImage.getId() == null) {
                boardImage.changeParentId(board.getId());
                imageRepository.save(boardImage);
            }
        }

        board.updateBoard(id, req.getType(), req.getProductId(), req.getContent(), req.getPrice(), req.getUpdatedBy());
        return boardRepository.save(board).toDTO();
    }

    @Transactional
    public void deleteBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(ResolutionException::new);
        imageRepository.deleteByIds(board.getImages().stream().map(Image::getId).toList());
        boardRepository.deleteById(id);
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
