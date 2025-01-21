package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.entity.User;
import com.pjw.retry_view.enums.UserAuth;
import com.pjw.retry_view.enums.UserState;
import com.pjw.retry_view.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class OAuthUserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public OAuthUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> params = super.loadUser(userRequest).getAttributes();
//        System.out.println("OAuth parmeters : "+params);
        OAuth2User oauthUser = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String loginId = registrationId+"_"+oauthUser.getAttribute("sub");
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        User user = userRepository.findByLoginId(loginId).orElse(User.builder()
                .name(name)
                .loginId(loginId)
                .email(email)
                .role(UserAuth.USER)
                .state(UserState.NORMAL)
                .build());

        //TODO - 로그인 시간 업데이트
        userRepository.save(user);
        return UserDetail.from(user);
    }
}
