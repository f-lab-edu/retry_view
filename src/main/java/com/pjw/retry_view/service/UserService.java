package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.UserDTO;
import com.pjw.retry_view.entity.User;
import com.pjw.retry_view.exception.UserNotFoundException;
import com.pjw.retry_view.repository.UserRepository;
import com.pjw.retry_view.request.LoginRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getUserList(){
        List<User> userList = userRepository.findAll();
        return userList.stream().map(User::toDTO).toList();
    }

    public UserDTO getUserInfo(String loginId){
        Optional<User> user = userRepository.findByLoginId(loginId);
        return user.map(User::toDTO).orElseThrow(UserNotFoundException::new);
    }

    public UserDTO userLogin(LoginRequest loginRequest){
        Optional<User> user = userRepository.findByLoginIdAndPassword(loginRequest.getLoginId(), loginRequest.getPassword());
        return user.map(User::toDTO).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public UserDTO saveUser(UserDTO userDTO){
        return userRepository.save(userDTO.toEntity()).toDTO();
    }

    @Transactional
    public void logout(String loginId){
        User user = userRepository.findByLoginId(loginId).orElseThrow(UserNotFoundException::new);
        user.setRefreshToken(null);
        userRepository.save(user);
    }
}
