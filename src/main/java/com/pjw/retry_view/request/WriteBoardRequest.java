package com.pjw.retry_view.request;

import com.pjw.retry_view.dto.BoardDTO;
import com.pjw.retry_view.dto.BoardImageDTO;
import com.pjw.retry_view.dto.BoardType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class WriteBoardRequest implements Serializable {
    private BoardType type;
    private Long productId;
    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String content;
    @NotNull(message = "가격은 필수 입력값입니다.")
    private Long price;
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
