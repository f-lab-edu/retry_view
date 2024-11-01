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
    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String content;
    private List<Image> images;
    private Long createdBy;
    private Long updatedBy;

    @Getter
    @Setter
    public static class Image {
        private Long id;
        private String imageUrl;

        public Image(){}

        public Image(String imageUrl){
            this.imageUrl = imageUrl;
        }

        public Image(Long id, String imageUrl){
            this.id = id;
            this.imageUrl = imageUrl;
        }
    }
}
