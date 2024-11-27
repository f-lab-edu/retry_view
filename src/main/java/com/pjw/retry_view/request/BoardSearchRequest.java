package com.pjw.retry_view.request;

import com.pjw.retry_view.enums.BoardType;
import com.pjw.retry_view.enums.SearchType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSearchRequest {
    private SearchType searchType;
    private String title;
    private BoardType type;
}
