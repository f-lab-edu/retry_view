package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.UserDTO;
import com.pjw.retry_view.entity.User;
import com.pjw.retry_view.exception.UserLoginFailedException;
import com.pjw.retry_view.exception.UserNotFoundException;
import com.pjw.retry_view.repository.UserRepository;
import com.pjw.retry_view.request.LoginRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getUserList(){
        List<User> userList = userRepository.findAll();
        return userList.stream().map(User::toDTO).toList();
    }

    public UserDTO getUserInfo(String loginId){
        Optional<User> user = userRepository.findByLoginId(loginId);
        return user.map(User::toDTO).orElseThrow(UserNotFoundException::new);
    }

    public UserDTO userLogin(LoginRequest loginRequest){
        User user = userRepository.findByLoginId(loginRequest.getLoginId()).orElseThrow(UserNotFoundException::new);
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw  new UserLoginFailedException("로그인이 실패하였습니다.");
        }
        return user.toDTO();
    }

    @Transactional
    public UserDTO saveUser(UserDTO userDTO){
        return userRepository.save(userDTO.toEntity()).toDTO();
    }

    @Transactional
    public UserDTO registUser(UserDTO userDTO){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(userDTO.toEntity()).toDTO();
    }

    @Transactional
    public void withdrawUser(String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
        user.withdraw();
        userRepository.save(user);
    }

    @Transactional
    public void logout(String loginId){
        User user = userRepository.findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
        user.setRefreshToken(null);
        userRepository.save(user);
    }
}
