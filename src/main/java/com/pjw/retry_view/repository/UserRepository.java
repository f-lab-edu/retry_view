package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.User;
import com.pjw.retry_view.request.LoginRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findAll();
    public Optional<User> findByLoginId(String loginId);
    public Optional<User> findByLoginIdAndPassword(String loginId, String password);
    public User save(User user);
}
