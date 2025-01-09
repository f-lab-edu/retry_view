package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.entity.User;
import com.pjw.retry_view.enums.UserAuth;
import com.pjw.retry_view.enums.UserState;
import com.pjw.retry_view.repositoryImpl.UserRepositoryImpl;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OAuthUserService extends DefaultOAuth2UserService {
    private final UserRepositoryImpl userRepositoryImpl;

    public OAuthUserService(UserRepositoryImpl userRepositoryImpl) {
        this.userRepositoryImpl = userRepositoryImpl;
    }

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //Map<String, Object> params = super.loadUser(userRequest).getAttributes();
        OAuth2User oauthUser = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String loginId = registrationId+"_"+oauthUser.getAttribute("sub");
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        User user = userRepositoryImpl.findByLoginId(loginId).orElse(User.builder()
                .name(name)
                .loginId(loginId)
                .email(email)
                .role(UserAuth.USER)
                .state(UserState.NORMAL)
                .build());

        //TODO - 로그인 시간 업데이트
        userRepositoryImpl.save(user);
        return UserDetail.from(user);
    }
}
