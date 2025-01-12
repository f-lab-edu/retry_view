package com.pjw.retry_view.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjw.retry_view.dto.ChatMessageView;
import com.pjw.retry_view.enums.ApiResponseCodeExamples;
import com.pjw.retry_view.enums.ErrorCode;
import com.pjw.retry_view.enums.MessageType;
import com.pjw.retry_view.exception.ChatMessageParseException;
import com.pjw.retry_view.request.MessageRequest;
import com.pjw.retry_view.service.ChatMessageService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final ChatMessageService chatMessageService;

    public ChatController(ObjectMapper objectMapper, RedisTemplate redisTemplate, ChatMessageService chatMessageService) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        this.chatMessageService = chatMessageService;
    }

    @GetMapping("/chat/{id}")
    @ApiResponseCodeExamples({ErrorCode.INVALID_TOKEN})
    public List<ChatMessageView> getMessageList(@PathVariable("id") Long id){
        return chatMessageService.findChatListByChatId(id);
    }

    @GetMapping("/chat/test")
    @ApiResponseCodeExamples({ErrorCode.CHAT_MESSAGE_PARSE, ErrorCode.INVALID_TOKEN})
    public void test(@RequestParam(name="message")String message, @RequestParam(name="type")String type, @RequestParam("chatId") Long chatId) {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessage(message);
        messageRequest.setType(MessageType.valueOf(type));
        messageRequest.setChatId(chatId);

        try {
            String json = objectMapper.writeValueAsString(messageRequest);
            redisTemplate.convertAndSend("chat", json);
        }catch(JsonProcessingException e){
            throw new ChatMessageParseException(e);
        }
    }

    @MessageMapping("/chat/msg") // 메시지 발행: /pub/chat/msg, 구독: /sub/chat/{chatId}
    public void publicshMessage(MessageRequest messageRequest){
        try {
            String json = objectMapper.writeValueAsString(messageRequest);
            redisTemplate.convertAndSend("chat", json);
            chatMessageService.saveChatMessage(messageRequest);
        }catch(JsonProcessingException e){
            throw new ChatMessageParseException(e);
        }
    }
}
