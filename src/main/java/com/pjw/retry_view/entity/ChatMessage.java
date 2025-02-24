package com.pjw.retry_view.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String message;
    private Long sender;
    private ZonedDateTime sendAt;

    @Builder
    public ChatMessage(Long id, Long chatId, String message, Long sender, ZonedDateTime sendAt) {
        this.id = id;
        this.chatId = chatId;
        this.message = message;
        this.sender = sender;
        this.sendAt = sendAt;
    }

    public static ChatMessage newOne(Long chatId, String message, Long sender){
        return ChatMessage.builder()
                .chatId(chatId)
                .message(message)
                .sender(sender)
                .sendAt(ZonedDateTime.now())
                .build();
    }
}
