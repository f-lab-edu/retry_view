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

    @GetMapping("/{id}")
    public BoardDTO getBoard(@PathVariable(name = "id") Long id){
        return boardService.getBoard(id);
    }

    @PostMapping
    public BoardDTO writeBoard(@RequestBody @Valid WriteBoardRequest board){
        return boardService.saveBoard(board);
    }

    @PutMapping("/{id}")
    public BoardDTO updateBoard(@RequestBody @Valid WriteBoardRequest board, @PathVariable(name = "id") Long id){
        return boardService.updateBoard(board, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable(name = "id") Long id){
        boardService.deleteBoard(id);
    }

}
