package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.ChatMessageView;
import com.pjw.retry_view.entity.ChatMessage;
import com.pjw.retry_view.repositoryImpl.ChatMessageRepositoryImpl;
import com.pjw.retry_view.request.MessageRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {
    private final ChatMessageRepositoryImpl chatMessageRepositoryImpl;

    public ChatMessageService(ChatMessageRepositoryImpl chatMessageRepositoryImpl) {
        this.chatMessageRepositoryImpl = chatMessageRepositoryImpl;
    }

    public List<ChatMessageView> findChatListByChatId(Long chatId){
        return chatMessageRepositoryImpl.findByChatId(chatId).stream().map(ChatMessageView::fromEntity).toList();
    }

    @Transactional
    public void saveChatMessage(MessageRequest req){
        ChatMessage message = ChatMessage.newOne(req.getChatId(), req.getMessage(), req.getSender());
        chatMessageRepositoryImpl.save(message);
    }
}
