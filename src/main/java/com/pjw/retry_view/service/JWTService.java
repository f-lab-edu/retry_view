package com.pjw.retry_view.service;

import com.pjw.retry_view.response.JWToken;
import com.pjw.retry_view.dto.UserDTO;
import com.pjw.retry_view.dto.UserInfo;
import com.pjw.retry_view.entity.User;
import com.pjw.retry_view.exception.InvalidTokenException;
import com.pjw.retry_view.exception.UserNotFoundException;
import com.pjw.retry_view.repository.UserRepository;
import com.pjw.retry_view.util.JWTUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    private final UserRepository userRepository;

    public JWTService(UserRepository userRepository){
        this.userRepository = userRepository;
    }



    @Transactional
    public JWToken renewAccessToken(String refreshToken) throws InvalidTokenException {
        if (JWTUtil.isValidateToken(refreshToken)) {
            throw new InvalidTokenException();
        }

        UserDTO user = userRepository.findByRefreshToken(refreshToken).map(User::toDTO).orElseThrow(UserNotFoundException::new);
        UserInfo userInfo = new UserInfo(user.getName(), user.getLoginId(), user.getRole());

        boolean isExpired = JWTUtil.isTokenExpired(refreshToken);
        if (isExpired) {
            refreshToken = JWTUtil.createRefreshToken();
            user.setRefreshToken(refreshToken);
            userRepository.save(user.toEntity());
            return JWToken.getJWT(JWTUtil.createAccessToken(userInfo), refreshToken);
        }

        return JWToken.getJWT(JWTUtil.createAccessToken(userInfo), null);
    }


}
