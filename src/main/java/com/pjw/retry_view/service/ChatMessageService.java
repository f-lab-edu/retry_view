package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.ChatMessageView;
import com.pjw.retry_view.entity.ChatMessage;
import com.pjw.retry_view.repository.ChatMessageRepository;
import com.pjw.retry_view.request.MessageRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public List<ChatMessageView> findChatListByChatId(Long chatId){
        return chatMessageRepository.findByChatId(chatId).stream().map(ChatMessageView::fromEntity).toList();
    }

    @Transactional
    public void saveChatMessage(MessageRequest req){
        ChatMessage message = ChatMessage.newOne(req.getChatId(), req.getMessage(), req.getSender());
        chatMessageRepository.save(message);
    }
}
