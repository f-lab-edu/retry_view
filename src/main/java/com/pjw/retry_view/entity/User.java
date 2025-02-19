package com.pjw.retry_view.entity;

import com.pjw.retry_view.converter.UserAutnEnumConverter;
import com.pjw.retry_view.converter.UserStateEnumConverter;
import com.pjw.retry_view.enums.Gender;
import com.pjw.retry_view.enums.UserAuth;
import com.pjw.retry_view.dto.UserView;
import com.pjw.retry_view.enums.UserState;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User {
    @EmbeddedId
    private UserId userId;
    private String name;
    private Gender gender;
    private String phone;
    private String email;
    private String address;
    @Convert(converter = UserAutnEnumConverter.class)
    private UserAuth role;
    private String loginId;
    private String password;
    private String nickname;
    private String type;
    @Convert(converter = UserStateEnumConverter.class)
    private UserState state;
    private String refreshToken;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    public void withdraw(){
        this.state = UserState.WITHDRAW;
        this.name = null;
        this.gender = null;
        this.email = null;
        this.phone = null;
        this.address = null;
        this.password = null;
        this.nickname = null;
        this.updatedAt = ZonedDateTime.now();
    }

    @Builder
    public User(UserId userId, String name, Gender gender, String phone, String email, String address, UserAuth role, String loginId, String password, String nickname, String type, UserState state, String refreshToken, Long createdBy, ZonedDateTime createdAt, Long updatedBy, ZonedDateTime updatedAt) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.type = type;
        this.state = state;
        this.refreshToken = refreshToken;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public UserView toDTO(){
        return UserView.builder()
                .id(userId.getId())
                .name(name)
                .gender(gender)
                .phone(phone)
                .email(email)
                .address(address)
                .role(role)
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .type(type)
                .state(state)
                .refreshToken(refreshToken)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .updatedBy(updatedBy)
                .updatedAt(updatedAt)
                .build();
    }
}
