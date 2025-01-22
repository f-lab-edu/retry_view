package com.pjw.retry_view.service;

import com.google.firebase.messaging.*;
import com.pjw.retry_view.dto.PushMessage;
import com.pjw.retry_view.exception.SendPushException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FCMService {

    public void sendMulticast(PushMessage pushMessage, Set<String> userTokens){
        Notification notification = Notification.builder()
                .setTitle(pushMessage.getTitle())
                .setBody(pushMessage.getBody())
                .build();
        WebpushConfig webpushConfig = WebpushConfig.builder()
                .setFcmOptions(WebpushFcmOptions.builder()
                        .setLink("http://localhost:8080").build())
                .build();
        MulticastMessage message = MulticastMessage.builder()
                .setWebpushConfig(webpushConfig)
                .addAllTokens(userTokens)
                .setNotification(notification)
                .build();

        try {
            FirebaseMessaging.getInstance().sendEachForMulticast(message);
        } catch (FirebaseMessagingException e) {
            throw new SendPushException(e);
        }
    }

    public void send(PushMessage pushMessage){
        Notification notification = Notification.builder()
                .setTitle(pushMessage.getTitle())
                .setBody(pushMessage.getBody())
                .build();
        WebpushConfig webpushConfig = WebpushConfig.builder()
                .setFcmOptions(WebpushFcmOptions.builder()
                        .setLink("http://localhost:8080").build())
                .build();
        Message message = Message.builder().
                setWebpushConfig(webpushConfig)
                .setToken(pushMessage.getToken())
                .setNotification(notification)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw new SendPushException(e);
        }
    }
}
