package com.pjw.retry_view.entity;

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
    private UserId userId;
    @Column(name = "type")
    private String type;
    @Column(name = "token")
    private String token;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Builder
    public UserDevice(Long id, UserId userId, String type, String token, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.token = token;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
