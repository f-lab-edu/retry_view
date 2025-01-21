package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.BoardLikeView;
import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.enums.ApiResponseCodeExamples;
import com.pjw.retry_view.enums.ErrorCode;
import com.pjw.retry_view.service.BoardLikeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시판 좋아요 API 컨트롤러", description = "")
@RestController
@RequestMapping("/like")
public class BoardLikeController {
    private final BoardLikeService boardLikeService;

    public BoardLikeController(BoardLikeService boardLikeService) {
        this.boardLikeService = boardLikeService;
    }

    @Operation(summary = "유저가 '좋아요'표시한 게시글 목록 조회 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.INVALID_TOKEN,})
    @GetMapping
    public List<BoardLikeView> getLikeList(@AuthenticationPrincipal UserDetail userDetail){
       return  boardLikeService.getUserBoardLikeList(userDetail.getId());
    }

    @Operation(summary = "게시글 좋아요 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @PostMapping("/{boardId}")
    public void saveLike(@AuthenticationPrincipal UserDetail userDetail, @PathVariable(name = "boardId") Long boardId){
        boardLikeService.saveBoardLike(boardId, userDetail.getId());
    }

    @Operation(summary = "게시글 좋아요 취소 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @DeleteMapping("/{boardId}")
    public void deleteLike(@AuthenticationPrincipal UserDetail userDetail, @PathVariable(name = "boardId") Long boardId){
        boardLikeService.deleteBoardLike(boardId, userDetail.getId());
    }
}
