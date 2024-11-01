package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.BoardLikeDTO;
import com.pjw.retry_view.service.BoardLikeService;
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
    public void saveLike(@PathVariable(name = "boardId") Long boardId){
       boardLikeService.saveBoardLike(boardId);
    }

    @DeleteMapping("/{boardId}")
    public void deleteLike(@PathVariable(name = "boardId") Long boardId){
        boardLikeService.deleteBoardLike(boardId);
    }
}
