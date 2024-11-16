package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.BoardLikeDTO;
import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.service.BoardLikeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/like")
public class BoardLikeController {
    private final BoardLikeService boardLikeService;

    public BoardLikeController(BoardLikeService boardLikeService) {
        this.boardLikeService = boardLikeService;
    }

    @GetMapping
    public List<BoardLikeDTO> getLikeList(){
       return  boardLikeService.getUserBoardLikeList();
    }

    @PostMapping("/{boardId}")
    public void saveLike(@AuthenticationPrincipal UserDetail userDetail, @PathVariable(name = "boardId") Long boardId){
        boardLikeService.saveBoardLike(boardId, userDetail.getId());
    }

    @DeleteMapping("/{boardId}")
    public void deleteLike(@AuthenticationPrincipal UserDetail userDetail, @PathVariable(name = "boardId") Long boardId){
        boardLikeService.deleteBoardLike(boardId, userDetail.getId());
    }
}
