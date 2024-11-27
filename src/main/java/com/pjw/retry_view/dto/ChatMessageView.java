package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.ChatMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageView {
    private Long id;
    private Long chatId;
    private String message;
    private Long sender;
    private ZonedDateTime sendAt;

    @Builder
    public ChatMessageView(Long id, Long chatId, String message, Long sender, ZonedDateTime sendAt) {
        this.id = id;
        this.chatId = chatId;
        this.message = message;
        this.sender = sender;
        this.sendAt = sendAt;
    }

    public static ChatMessageView fromEntity(ChatMessage msg){
        return ChatMessageView.builder()
                .id(msg.getId())
                .chatId(msg.getChatId())
                .message(msg.getMessage())
                .sender(msg.getSender())
                .sendAt(msg.getSendAt())
                .build();
    }
}
