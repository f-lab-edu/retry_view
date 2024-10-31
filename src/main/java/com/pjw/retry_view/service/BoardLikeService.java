package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.BoardLikeDTO;
import com.pjw.retry_view.entity.BoardLike;
import com.pjw.retry_view.entity.LikeId;
import com.pjw.retry_view.repository.BoardLikeRepository;
import com.pjw.retry_view.util.JWTUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardLikeService {
    private final BoardLikeRepository boardLikeRepository;

    public BoardLikeService(BoardLikeRepository boardLikeRepository) {
        this.boardLikeRepository = boardLikeRepository;
    }

    public List<BoardLikeDTO> getUserBoardLikeList(){
        Long userId = JWTUtil.getUserId();
        return boardLikeRepository.findByIdUserId(userId).stream().map(BoardLike::toDTO).toList();
    }

    @Transactional
    public void saveBoardLike(Long boardId){
        Long userId = JWTUtil.getUserId();
        BoardLike like = BoardLike.newOne(userId, boardId);
        boardLikeRepository.save(like);
    }

    @Transactional
    public void deleteBoardLike(Long boardId){
        Long userId = JWTUtil.getUserId();
        boardLikeRepository.deleteById(LikeId.newOne(userId, boardId));
    }
}
