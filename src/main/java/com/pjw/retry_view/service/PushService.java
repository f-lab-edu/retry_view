package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.PushMessage;
import com.pjw.retry_view.entity.UserDevice;
import com.pjw.retry_view.repositoryImpl.UserDeviceRepositoryImpl;
import com.pjw.retry_view.request.PushRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PushService {
    private final UserDeviceRepositoryImpl userDeviceRepositoryImpl;
    private final FCMService fcmService;

    public PushService(UserDeviceRepositoryImpl userDeviceRepositoryImpl, FCMService fcmService) {
        this.userDeviceRepositoryImpl = userDeviceRepositoryImpl;
        this.fcmService = fcmService;
    }

    @Transactional
    public void send(PushRequest req){
        List<UserDevice> list = userDeviceRepositoryImpl.findByUserIdIn(req.getUserIds());
        PushMessage msg = PushMessage.from(null,req.getTitle(),req.getBody());
        Set<String> userTokens = list.stream().map(UserDevice::getToken).collect(Collectors.toSet());
        fcmService.sendMulticast(msg, userTokens);
    }

}
