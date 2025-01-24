package com.pjw.retry_view.repository;

import com.pjw.retry_view.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    List<UserDevice> findByUserIdIn(Set<Long> userId);
    List<UserDevice> findByUserId(Long userId);
    UserDevice save(UserDevice userDevice);
}
