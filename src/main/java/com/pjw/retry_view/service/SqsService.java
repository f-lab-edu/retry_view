package com.pjw.retry_view.service;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class SqsService {

    @SqsListener("retry-view-sqs")
    public void receiveSqsMessage(String message){
        System.out.println(message);
    }
}
