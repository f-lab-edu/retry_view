package com.pjw.retry_view.event;

import com.pjw.retry_view.dto.PushMessage;
import com.pjw.retry_view.entity.Board;
import com.pjw.retry_view.entity.UserDevice;
import com.pjw.retry_view.repositoryImpl.BoardRepositoryImpl;
import com.pjw.retry_view.repositoryImpl.UserDeviceRepositoryImpl;
import com.pjw.retry_view.service.FCMService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PushEventListener {
    private final BoardRepositoryImpl boardRepositoryImpl;
    private final UserDeviceRepositoryImpl userDeviceRepositoryImpl;
    private final FCMService fcmService;

    public PushEventListener(BoardRepositoryImpl boardRepositoryImpl, UserDeviceRepositoryImpl userDeviceRepositoryImpl, FCMService fcmService) {
        this.boardRepositoryImpl = boardRepositoryImpl;
        this.userDeviceRepositoryImpl = userDeviceRepositoryImpl;
        this.fcmService = fcmService;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void likedBoardPushEvent(LikedEvent event){
        Set<Long> boardIds = event.getBoardIds();

        if(boardIds == null || boardIds.isEmpty()) return;

        Set<Long> userIds = boardRepositoryImpl.findByIdIn(boardIds).stream().map(Board::getCreatedBy).collect(Collectors.toSet());
        Set<String> userTokens = userDeviceRepositoryImpl.findByUserIdIn(userIds).stream().map(UserDevice::getToken).collect(Collectors.toSet());

        PushMessage msg = PushMessage.makeLikePushMessssage("token");
        fcmService.sendMulticast(msg, userTokens);
    }
}
