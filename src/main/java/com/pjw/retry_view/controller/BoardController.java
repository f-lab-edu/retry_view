package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.enums.SearchType;
import com.pjw.retry_view.request.WriteBoardRequest;
import com.pjw.retry_view.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시판 API 컨트롤러", description = "중고 거래 구매/판매 게시글 API")
@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @Operation(summary = "게시글 목록 조회 API", description = "")
    @GetMapping
    public List<BoardDTO> getBoardList(
                @RequestParam(name = "cursor", required = false) Long cursor,
                @RequestParam(name = "searchType", required = false) SearchType searchType,
                @RequestParam(name = "content", required = false) String content
    ){
        return boardService.getBoardList(cursor, searchType, content);
    }

    @Operation(summary = "특정 게시글 조회 API", description = "")
    @GetMapping("/{id}")
    public BoardDTO getBoard(@PathVariable(name = "id") Long id){
        return boardService.getBoard(id);
    }

    @Operation(summary = "게시글 작성 API", description = "")
    @PostMapping
    public BoardDTO writeBoard(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteBoardRequest board){
        board.setCreatedBy(userDetail.getId());
        return boardService.saveBoard(board);
    }

    @Operation(summary = "게시글 수정 API", description = "")
    @PutMapping("/{id}")
    public BoardDTO updateBoard(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteBoardRequest board, @PathVariable(name = "id") Long id){
        board.setUpdatedBy(userDetail.getId());
        return boardService.updateBoard(board, id);
    }

    @Operation(summary = "게시글 삭제 API", description = "")
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable(name = "id") Long id){
        boardService.deleteBoard(id);
    }

}
