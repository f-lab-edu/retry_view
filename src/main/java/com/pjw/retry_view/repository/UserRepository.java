package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.User;
import com.pjw.retry_view.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {
    public List<User> findAll();
    public Optional<User> findById(UserId id);
    public Optional<User> findByLoginId(String loginId);
    public Optional<User> findByLoginIdAndPassword(String loginId, String password);
    public Optional<User> findByRefreshToken(String refreshToken);
    public User save(User user);
}
