package com.pjw.retry_view.service;

import com.pjw.retry_view.response.JWToken;
import com.pjw.retry_view.dto.UserView;
import com.pjw.retry_view.dto.UserInfo;
import com.pjw.retry_view.entity.User;
import com.pjw.retry_view.exception.InvalidTokenException;
import com.pjw.retry_view.exception.UserNotFoundException;
import com.pjw.retry_view.repositoryImpl.UserRepositoryImpl;
import com.pjw.retry_view.util.JWTUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    private final UserRepositoryImpl userRepositoryImpl;

    public JWTService(UserRepositoryImpl userRepositoryImpl){
        this.userRepositoryImpl = userRepositoryImpl;
    }

    @Transactional
    public JWToken renewAccessToken(String refreshToken) throws InvalidTokenException {
        if (JWTUtil.isValidateToken(refreshToken)) {
            throw new InvalidTokenException();
        }

        UserView user = userRepositoryImpl.findByRefreshToken(refreshToken).map(User::toDTO).orElseThrow(UserNotFoundException::new);
        UserInfo userInfo = new UserInfo(user.getId(), user.getName(), user.getLoginId(), user.getRole());

        boolean isExpired = JWTUtil.isTokenExpired(refreshToken);
        if (isExpired) {
            refreshToken = JWTUtil.createRefreshToken();
            user.setRefreshToken(refreshToken);
            userRepositoryImpl.save(user.toEntity());
            return JWToken.getJWT(JWTUtil.createAccessToken(userInfo), refreshToken);
        }

        return JWToken.getJWT(JWTUtil.createAccessToken(userInfo), null);
    }


}
