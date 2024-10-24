package com.pjw.retry_view.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class WriteEventRequest implements Serializable {
    private Long id;
    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String content;
    private List<Image> images;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private ZonedDateTime startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private ZonedDateTime endAt;
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
