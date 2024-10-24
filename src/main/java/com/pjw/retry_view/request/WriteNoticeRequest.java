package com.pjw.retry_view.request;

import com.pjw.retry_view.dto.BoardType;
import com.pjw.retry_view.dto.NoticeImageDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Setter
public class WriteNoticeRequest {
    private Long id;
    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String content;
    private List<String> images;
    private Long createdBy;

    private List<NoticeImageDTO> gg(){
        if(CollectionUtils.isEmpty(images)) return null;
        return images.stream().map(NoticeImageDTO::getImageDTO).toList();
    }
}
