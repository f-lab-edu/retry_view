package com.pjw.retry_view.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjw.retry_view.dto.MessageType;
import com.pjw.retry_view.exception.ChatMessageParseException;
import com.pjw.retry_view.request.MessageRequest;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RedisMessageSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public RedisMessageSubscriber(ObjectMapper objectMapper, RedisTemplate redisTemplate, SimpMessageSendingOperations simpMessageSendingOperations) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) { // 구독자들에게 메시지 전달
        try{
            String strMessage = Objects.requireNonNull(redisTemplate.getStringSerializer().deserialize(message.getBody())).toString();
            MessageRequest messageReq = objectMapper.readValue(strMessage, MessageRequest.class);
            if(MessageType.MESSAGE == messageReq.getType()){
                simpMessageSendingOperations.convertAndSend("/sub/chat/"+messageReq.getChatId(), messageReq);
            }
        }catch (Exception e){
            throw new ChatMessageParseException(e);
        }
    }
}
