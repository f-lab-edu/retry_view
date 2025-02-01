package com.pjw.retry_view.entity;

import com.pjw.retry_view.converter.DeviceTypeConverter;
import com.pjw.retry_view.enums.DeviceType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@Table(name = "user_device")
@Entity
public class UserDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "type")
    @Convert(converter = DeviceTypeConverter.class)
    private DeviceType type;
    @Column(name = "token")
    private String token;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Builder
    public UserDevice(Long id, Long userId, DeviceType type, String token, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.token = token;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public void tokenUpdate(DeviceType type, String token){
        this.type = type;
        this.token = token;
        this.updatedAt = ZonedDateTime.now();
    }
    public static UserDevice newOne(Long userId, DeviceType type, String token){
        ZonedDateTime now = ZonedDateTime.now();
        return UserDevice.builder()
                .userId(userId)
                .type(type)
                .token(token)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
