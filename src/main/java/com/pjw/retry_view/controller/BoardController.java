package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.enums.SearchType;
import com.pjw.retry_view.request.WriteBoardRequest;
import com.pjw.retry_view.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public List<BoardDTO> getBoardList(
                @RequestParam(name = "cursor", required = false) Long cursor,
                @RequestParam(name = "searchType", required = false) SearchType searchType,
                @RequestParam(name = "content", required = false) String content
    ){
        return boardService.getBoardList(cursor, searchType, content);
    }

    @GetMapping("/{id}")
    public BoardDTO getBoard(@PathVariable(name = "id") Long id){
        return boardService.getBoard(id);
    }

    @PostMapping
    public BoardDTO writeBoard(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteBoardRequest board){
        board.setCreatedBy(userDetail.getId());
        return boardService.saveBoard(board);
    }

    @PutMapping("/{id}")
    public BoardDTO updateBoard(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteBoardRequest board, @PathVariable(name = "id") Long id){
        board.setUpdatedBy(userDetail.getId());
        return boardService.updateBoard(board, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable(name = "id") Long id){
        boardService.deleteBoard(id);
    }

}
