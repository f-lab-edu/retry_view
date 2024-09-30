package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.request.WriteBoardRequest;
import com.pjw.retry_view.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BoardDTO> writeBoard(@RequestBody @Valid WriteBoardRequest board){
        BoardDTO result = boardService.saveBoard(board.toBoardDTO());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
