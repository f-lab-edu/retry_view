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
        List<UserDTO> result = userList.stream().map(user->user.toDTO()).toList();
        return result;
    }

    public UserDTO getUserInfo(String loginId){
        Optional<User> user = userRepository.findByLoginId(loginId);
        return user.map(User::toDTO).orElse(null);
    }

    public UserDTO userLogin(LoginRequest loginRequest){
        User userParam = new User();
        userParam.setLoginId(loginRequest.getLoginId());
        userParam.setPassword(loginRequest.getPassword());
        Optional<User> user = userRepository.findByLoginIdAndPassword(loginRequest.getLoginId(), loginRequest.getPassword());
        return user.map(User::toDTO).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public UserDTO insertUser(UserDTO userDTO){
        UserDTO result = userRepository.save(userDTO.toEntity()).toDTO();
        return result;
    }
}
