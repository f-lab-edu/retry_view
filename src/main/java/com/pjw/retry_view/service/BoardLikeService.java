package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.BoardLikeView;
import com.pjw.retry_view.dto.PushMessage;
import com.pjw.retry_view.entity.BoardLike;
import com.pjw.retry_view.entity.LikeId;
import com.pjw.retry_view.entity.User;
import com.pjw.retry_view.entity.UserId;
import com.pjw.retry_view.exception.UserNotFoundException;
import com.pjw.retry_view.repositoryImpl.BoardLikeRepositoryImpl;
import com.pjw.retry_view.repositoryImpl.UserRepositoryImpl;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BoardLikeService {
    private final BoardLikeRepositoryImpl boardLikeRepositoryImpl;
    private final UserRepositoryImpl userRepositoryImpl;
    private final RedisTemplate<String, String> redisTemplate;
    private final FCMService fcmService;
    private final SnsService snsService;
    private static final String HASH_KEY = "Likes";

    public BoardLikeService(BoardLikeRepositoryImpl boardLikeRepositoryImpl, UserRepositoryImpl userRepositoryImpl, RedisTemplate<String, String> redisTemplate, FCMService fcmService, SnsService snsService) {
        this.boardLikeRepositoryImpl = boardLikeRepositoryImpl;
        this.userRepositoryImpl = userRepositoryImpl;
        this.redisTemplate = redisTemplate;
        this.fcmService = fcmService;
        this.snsService = snsService;
    }

    public List<BoardLikeView> getUserBoardLikeList(Long userId){
        return boardLikeRepositoryImpl.findByIdUserId(userId).stream().map(BoardLike::toDTO).toList();
    }

    @Transactional
    public void saveBoardLike(Long boardId, Long userId){
        // HASH_KEY, boardId, set(userIds)
        HashOperations<String, String, String> hashOper = redisTemplate.opsForHash();

        LikeId id = LikeId.newOne(userId, boardId);
        BoardLike boardLike = boardLikeRepositoryImpl.findById(id).orElse(null);
        if(boardLike != null) return;

        String strBoardId = String.valueOf(boardId);
        String strUserId = String.valueOf(userId);

        Set<String> userIds = userIdsToSet(hashOper.get(HASH_KEY, strBoardId));
        userIds.add(strUserId);
        String strUserIds = String.join(",",userIds);

        hashOper.put(HASH_KEY, strBoardId, strUserIds);

        User user = userRepositoryImpl.findById(UserId.of(userId)).orElseThrow(UserNotFoundException::new);
        push(user.getDeviceToken());
        publish();
    }

    public void push(String deviceToken){
        if(StringUtils.isBlank(deviceToken)) return;
        PushMessage message = PushMessage.getLikePushMessssage(deviceToken);
        fcmService.send(message);
    }

    public void publish(){
        Map<String, Object> message = new HashMap<>();
        message.put("type","like");
        snsService.publishSns("NewPost", message);
    }

    @Scheduled(fixedDelay = 60000) //1분 단위로 실행
    @Transactional
    public void saveBoardLikeFromRedis(){
        HashOperations<String, String, String> hashOper = redisTemplate.opsForHash();
        Set<String> boardIds = hashOper.keys(HASH_KEY);

        List<BoardLike> boardLikes = new ArrayList<>();
        for(String boardId : boardIds){
            Set<String> userIds = userIdsToSet(hashOper.get(HASH_KEY, boardId));

            for(String userId : userIds) {
                BoardLike like = BoardLike.newOne(Long.valueOf(userId), Long.valueOf(boardId));
                boardLikes.add(like);
            }

            hashOper.delete(HASH_KEY, boardId);
        }
        boardLikeRepositoryImpl.saveAll(boardLikes);
    }

    @Transactional
    public void deleteBoardLike(Long boardId, Long userId){
        if(isBoardLikedWithRedis(userId, boardId)) deleteBoardLikeWithRedis(userId, boardId);
        else boardLikeRepositoryImpl.deleteById(LikeId.newOne(userId, boardId));
    }

    @Transactional
    public void deleteBoardLikeWithRedis(Long userId, Long boardId){
        HashOperations<String, String, String> hashOper = redisTemplate.opsForHash();
        String strBoardId = String.valueOf(boardId);
        String strUserId = String.valueOf(userId);

        Set<String> userIds = userIdsToSet(hashOper.get(HASH_KEY, strBoardId));
        userIds.remove(strUserId);

        String strUserIds = String.join(",",userIds);
        hashOper.put(HASH_KEY, strBoardId, strUserIds);
    }

    public boolean isBoardLikedWithRedis(Long userId, Long boardId){
        HashOperations<String, String, String> hashOper = redisTemplate.opsForHash();
        String strBoardId = String.valueOf(boardId);
        String strUserId = String.valueOf(userId);
        Set<String> userIds = userIdsToSet(hashOper.get(HASH_KEY, strBoardId));
        return userIds.contains(String.valueOf(strUserId));
    }

    private Set<String> userIdsToSet(String userIds){
        Set<String> result = new HashSet<>();
        if(StringUtils.isBlank(userIds)) return result;

        StringTokenizer token = new StringTokenizer(userIds, ",");
        while(token.hasMoreElements()){
            result.add(token.nextToken());
        }
        return result;
    }
}
