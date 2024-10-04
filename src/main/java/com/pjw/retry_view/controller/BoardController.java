package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.request.WriteBoardRequest;
import com.pjw.retry_view.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping
    public List<BoardDTO> getBoardList(){
        return boardService.getBoardList();
    }

    @PostMapping
    public BoardDTO writeBoard(@RequestBody @Valid WriteBoardRequest board){
        return boardService.saveBoard(board);
    }
}
