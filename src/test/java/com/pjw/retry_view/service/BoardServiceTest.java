package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.entity.Board;
import com.pjw.retry_view.enums.BoardType;
import com.pjw.retry_view.enums.SearchType;
import com.pjw.retry_view.repository.BoardRepository;
import com.pjw.retry_view.repository.ImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {
    @Mock
    BoardRepository boardRepository;
    @Mock
    ImageRepository imageRepository;

    @InjectMocks
    BoardService boardService;

    private static final int DEFAULT_PAGE_SIZE = 3;
    Pageable pageable = PageRequest.of(0, DEFAULT_PAGE_SIZE);

    @Test
    @DisplayName("게시판 조회 테스트 - 제목 검색 기준")
    public void getBoardListWhereTitle(){
        Long cursor = 10L;
        String content = "title";
        List<Board> boardList = getTestBoardList();

        when(boardRepository.findByIdLessThanAndTitleLikeOrderByIdDesc(anyLong(), anyString(), any(Pageable.class))).thenReturn(boardList);

        List<BoardDTO> result = boardService.getBoardList(cursor, SearchType.TITLE, content);

        assertThat(result).hasSize(DEFAULT_PAGE_SIZE);
        assertThat(result.get(0).getTitle()).contains(content);
    }

    @Test
    @DisplayName("게시판 조회 테스트 - 타입 검색 기준")
    public void getBoardListWhereType(){
        Long cursor = 10L;
        String content = "Sell";
        List<Board> boardList = getTestBoardList();

        when(boardRepository.findByIdLessThanAndTypeOrderByIdDesc(anyLong(), any(BoardType.class), any(Pageable.class))).thenReturn(boardList);

        List<BoardDTO> result = boardService.getBoardList(cursor, SearchType.TYPE, content);

        assertThat(result).hasSize(DEFAULT_PAGE_SIZE);
        assertThat(result.get(0).getType()).isEqualTo(BoardType.SELL.getCode());
    }

    @Test
    @DisplayName("게시판 조회 테스트 - 전체 기준")
    public void getBoardListAll(){
        Long cursor = 10L;
        String content = null;
        List<Board> boardList = getTestBoardList();

        when(boardRepository.findAllByOrderByIdDesc(any(Pageable.class))).thenReturn(boardList);

        List<BoardDTO> result = boardService.getBoardList(cursor, SearchType.ALL, content);

        assertThat(result).hasSize(DEFAULT_PAGE_SIZE);
    }

    public List<Board> getTestBoardList(){
        List<Board> result = new ArrayList<>();
        for(int i = DEFAULT_PAGE_SIZE; i > 0; i--){
            result.add(Board.builder()
                .title("title"+i)
                .type(BoardType.SELL)
                            .imageIds(new ArrayList<>())
                .createdBy(1L)
                .build());
        }
        return result;
    }
}
