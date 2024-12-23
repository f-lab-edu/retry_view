package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.BoardLikeView;
import com.pjw.retry_view.entity.BoardLike;
import com.pjw.retry_view.entity.LikeId;
import com.pjw.retry_view.repository.BoardLikeRepository;
import com.pjw.retry_view.util.JWTUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BoardLikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String HASH_KEY = "Likes";

    public BoardLikeService(BoardLikeRepository boardLikeRepository, RedisTemplate<String, String> redisTemplate) {
        this.boardLikeRepository = boardLikeRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<BoardLikeView> getUserBoardLikeList(){
        Long userId = JWTUtil.getUserId();
        return boardLikeRepository.findByIdUserId(userId).stream().map(BoardLike::toDTO).toList();
    }

    @Transactional
    public void saveBoardLike(Long boardId, Long userId){
        // HASH_KEY, boardId, set(userIds)
        HashOperations<String, String, String> hashOper = redisTemplate.opsForHash();

        LikeId id = LikeId.newOne(userId, boardId);
        BoardLike boardLike = boardLikeRepository.findById(id).orElse(null);
        if(boardLike != null) return;

        String strBoardId = String.valueOf(boardId);
        String strUserId = String.valueOf(userId);

        Set<String> userIds = userIdsToSet(hashOper.get(HASH_KEY, strBoardId));
        userIds.add(strUserId);
        String strUserIds = String.join(",",userIds);

        hashOper.put(HASH_KEY, strBoardId, strUserIds);
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
        boardLikeRepository.saveAll(boardLikes);
    }

    @Transactional
    public void deleteBoardLike(Long boardId, Long userId){
        if(isBoardLikedWithRedis(userId, boardId)) deleteBoardLikeWithRedis(userId, boardId);
        else boardLikeRepository.deleteById(LikeId.newOne(userId, boardId));
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
