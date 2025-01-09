package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.UserView;
import com.pjw.retry_view.entity.User;
import com.pjw.retry_view.exception.UserLoginFailedException;
import com.pjw.retry_view.exception.UserNotFoundException;
import com.pjw.retry_view.repositoryImpl.UserRepositoryImpl;
import com.pjw.retry_view.request.LoginRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepositoryImpl userRepositoryImpl;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepositoryImpl userRepositoryImpl, PasswordEncoder passwordEncoder) {
        this.userRepositoryImpl = userRepositoryImpl;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserView> getUserList(){
        List<User> userList = userRepositoryImpl.findAll();
        return userList.stream().map(User::toDTO).toList();
    }

    public UserView getUserInfo(String loginId){
        Optional<User> user = userRepositoryImpl.findByLoginId(loginId);
        return user.map(User::toDTO).orElseThrow(UserNotFoundException::new);
    }

    public UserView userLogin(LoginRequest loginRequest){
        User user = userRepositoryImpl.findByLoginId(loginRequest.getLoginId()).orElseThrow(UserNotFoundException::new);
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw  new UserLoginFailedException("로그인이 실패하였습니다.");
        }
        return user.toDTO();
    }

    @Transactional
    public UserView saveUser(UserView userView){
        return userRepositoryImpl.save(userView.toEntity()).toDTO();
    }

    @Transactional
    public UserView registUser(UserView userView){
        userView.setPassword(passwordEncoder.encode(userView.getPassword()));
        return userRepositoryImpl.save(userView.toEntity()).toDTO();
    }

    @Transactional
    public void withdrawUser(String loginId) {
        User user = userRepositoryImpl.findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
        user.withdraw();
        userRepositoryImpl.save(user);
    }

    @Transactional
    public void logout(String loginId){
        User user = userRepositoryImpl.findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
        user.setRefreshToken(null);
        userRepositoryImpl.save(user);
    }
}
