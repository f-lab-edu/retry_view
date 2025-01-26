package com.pjw.retry_view.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjw.retry_view.exception.MessageParseException;
import com.pjw.retry_view.request.WriteBoardRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.HashMap;
import java.util.Map;

@Service
public class SnsService {
    @Value("${spring.cloud.aws.sns.topic.arn}")
    private String arn;

    private final SnsClient snsClient;
    private final ObjectMapper objectMapper;

    public SnsService(SnsClient snsClient, ObjectMapper objectMapper) {
        this.snsClient = snsClient;
        this.objectMapper = objectMapper;
    }

    public void publishNewBoard(WriteBoardRequest req){
        Map<String, Object> message = new HashMap<>();
        message.put("type", "board");
        message.put("boardTitle", req.getTitle());
        message.put("boardType", req.getType().getCode());
        publishSns("NewBoard", message);
    }

    public void publishBoardLike(){
        Map<String, Object> message = new HashMap<>();
        message.put("type","like");
        publishSns("BoardLike", message);
    }

    public PublishResponse publishSns(String subject, Map<String, Object> message){
        String json;

        try {
            json = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new MessageParseException("메시지 변환에 실패했습니다.");
        }

        PublishRequest req = PublishRequest.builder()
                .topicArn(arn)
                .subject(subject)
                .message(json)
                .build();

        return snsClient.publish(req);
    }
}
