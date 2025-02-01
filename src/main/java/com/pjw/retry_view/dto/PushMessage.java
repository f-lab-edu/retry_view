package com.pjw.retry_view.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PushMessage {
    private String token;
    private String title;
    private String body;

    @Builder
    public PushMessage(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }

    public static PushMessage getLikePushMessssage(String token){
        return PushMessage.builder()
                .token(token)
                .title("RetryView")
                .body("좋아요 알림!")
                .build();
    }

    public static PushMessage from(String token, String title, String body){
        return PushMessage.builder()
                .token(token)
                .title(title)
                .body(body)
                .build();
    }
}
