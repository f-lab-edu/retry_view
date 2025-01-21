package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.BoardView;
import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.enums.ApiResponseCodeExamples;
import com.pjw.retry_view.enums.ErrorCode;
import com.pjw.retry_view.enums.SearchType;
import com.pjw.retry_view.request.DeleteRequest;
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
    @ApiResponseCodeExamples({ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.INVALID_TOKEN})
    @GetMapping
    public List<BoardView> getBoardList(
                @RequestParam(name = "cursor", required = false) Long cursor,
                @RequestParam(name = "searchType", required = false) SearchType searchType,
                @RequestParam(name = "content", required = false) String content
    ){
        return boardService.getBoardList(cursor, searchType, content);
    }

    @Operation(summary = "특정 게시글 조회 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.INVALID_TOKEN})
    @GetMapping("/{id}")
    public BoardView getBoard(@PathVariable(name = "id") Long id){
        return boardService.getBoard(id);
    }

    @Operation(summary = "게시글 작성 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @PostMapping
    public BoardView writeBoard(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteBoardRequest board){
        board.setCreatedBy(userDetail.getId());
        return boardService.saveBoard(board);
    }

    @Operation(summary = "게시글 수정 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.NOT_MY_RESOURCE, ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @PutMapping
    public BoardView updateBoard(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid WriteBoardRequest board){
        board.setUpdatedBy(userDetail.getId());
        return boardService.updateBoard(board);
    }

    @Operation(summary = "게시글 삭제 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.NOT_MY_RESOURCE, ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @DeleteMapping
    public void deleteBoard(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid DeleteRequest req){
        boardService.deleteBoard(req.getId(), userDetail.getId());
    }

}
