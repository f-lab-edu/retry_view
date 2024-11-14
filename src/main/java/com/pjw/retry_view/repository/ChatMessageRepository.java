package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    public List<ChatMessage> findByChatId(Long chatId);
    public ChatMessage save(ChatMessage chatMessage);
}
