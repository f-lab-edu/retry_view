package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.entity.Board;
import com.pjw.retry_view.entity.BoardImage;
import com.pjw.retry_view.entity.EventImage;
import com.pjw.retry_view.exception.ResourceNotFoundException;
import com.pjw.retry_view.repository.BoardImageRepository;
import com.pjw.retry_view.repository.BoardRepository;
import com.pjw.retry_view.request.WriteBoardRequest;
import com.pjw.retry_view.request.WriteEventRequest;
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
    private final BoardImageRepository boardImageRepository;

    public BoardService(BoardRepository boardRepository, BoardImageRepository boardImageRepository){
        this.boardRepository = boardRepository;
        this.boardImageRepository = boardImageRepository;
    }

    public List<BoardDTO> getBoardList(){
        return boardRepository.findAll().stream().map(Board::toDTO).toList();
    }

    public BoardDTO getBoard(Long id){
        return boardRepository.findById(id).orElseThrow(ResourceNotFoundException::new).toDTO();
    }

    @Transactional
    public BoardDTO saveBoard(WriteBoardRequest req){
        Board board = Board.newOne(req.getType(), req.getProductId(), req.getContent(), req.getPrice(), req.getCreatedBy());
        List<BoardImage> images = req.getImages().stream().map(img -> BoardImage.newOne(null, img.getImageUrl(), req.getCreatedBy())).toList();
        boardRepository.save(board);

        for(BoardImage boardImage : images){
            boardImage.changeBoard(board);
            boardImageRepository.save(boardImage);
        }

        board.changeBoardImage(images);
        return board.toDTO();
    }

    @Transactional
    public BoardDTO updateBoard(WriteBoardRequest req, Long id){
        Board board = boardRepository.findById(id).orElseThrow(ResolutionException::new);
        List<BoardImage> reqImages = req.getImages().stream().map(img -> BoardImage.newOne(img.getId(), img.getImageUrl(), req.getCreatedBy())).toList();

        List<Long> imageIds = req.getImages().stream().map(WriteBoardRequest.Image::getId).filter(Objects::nonNull).toList();
        List<Long> oldImageIds = board.getBoardImage().stream().map(BoardImage::getId).toList();
        List<Long> deleteImageIds = getDeleteImageIds(imageIds, oldImageIds);
        if(!CollectionUtils.isEmpty(deleteImageIds)) {
            boardImageRepository.deleteByIds(deleteImageIds);
            board.getBoardImage().removeIf(img->deleteImageIds.contains(img.getId()));
        }

        for(BoardImage boardImage : reqImages){
            if(boardImage.getId() == null) {
                boardImage.changeBoard(board);
                boardImageRepository.save(boardImage);
            }
        }

        board.updateBoard(id, req.getType(), req.getProductId(), req.getContent(), req.getPrice(), req.getUpdatedBy());
        return boardRepository.save(board).toDTO();
    }

    @Transactional
    public void deleteBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(ResolutionException::new);
        boardImageRepository.deleteByIds(board.getBoardImage().stream().map(BoardImage::getId).toList());
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
