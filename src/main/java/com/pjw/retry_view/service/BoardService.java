package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.entity.Board;
import com.pjw.retry_view.entity.BoardImage;
import com.pjw.retry_view.repository.BoardRepository;
import com.pjw.retry_view.request.WriteBoardRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public List<BoardDTO> getBoardList(){
        return boardRepository.findAll().stream().map(Board::toDTO).toList();
    }

    @Transactional
    public BoardDTO saveBoard(BoardDTO boardDTO){
        Board board = boardDTO.toEntity();
        List<BoardImage> images = boardDTO.getImages().stream().map(img -> BoardImage.getBoardImage(board, img.getImageUrl())).toList();
        board.setBoardImage(images);
        return boardRepository.save(board).toDTO();
    }
}
