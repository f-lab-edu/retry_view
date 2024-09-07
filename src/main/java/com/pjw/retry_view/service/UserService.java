package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.UserDTO;
import com.pjw.retry_view.entity.User;
import com.pjw.retry_view.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getUserList(){
        List<User> userList = userRepository.findAll();
        List<UserDTO> result = userList.stream().map(user->user.toDTO()).toList();
        return result;
    }
}
