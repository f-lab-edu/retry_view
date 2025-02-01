package com.pjw.retry_view.service;

import com.pjw.retry_view.entity.UserDevice;
import com.pjw.retry_view.repositoryImpl.UserDeviceRepositoryImpl;
import com.pjw.retry_view.request.UserDeviceRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UserDeviceService {
    private final UserDeviceRepositoryImpl userDeviceRepositoryImpl;
    private static final int MAX_DEVICE_COUNT = 3;

    public UserDeviceService(UserDeviceRepositoryImpl userDeviceRepositoryImpl) {
        this.userDeviceRepositoryImpl = userDeviceRepositoryImpl;
    }

    @Transactional
    public void saveUserDevice(UserDeviceRequest req, Long userId){
        List<UserDevice> devices = userDeviceRepositoryImpl.findByUserId(userId);
        UserDevice userDevice = null;
        if(devices.size() >= MAX_DEVICE_COUNT) {
            devices.sort(Comparator.comparing(UserDevice::getUpdatedAt));
            userDevice = devices.get(0);
            userDevice.tokenUpdate(req.getType(), req.getToken());
        } else {
            userDevice = UserDevice.newOne(userId, req.getType(), req.getToken());
        }

        userDeviceRepositoryImpl.save(userDevice);
    }
}
