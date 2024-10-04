package com.pjw.retry_view.entity;

import com.pjw.retry_view.converter.UserAutnEnumConverter;
import com.pjw.retry_view.converter.UserStateEnumConverter;
import com.pjw.retry_view.dto.Gender;
import com.pjw.retry_view.dto.UserAuth;
import com.pjw.retry_view.dto.UserDTO;
import com.pjw.retry_view.dto.UserState;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "role")
    @Convert(converter = UserAutnEnumConverter.class)
    private UserAuth role;
    @Column(name = "login_id")
    private String loginId;
    @Column(name = "password")
    private String password;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "type")
    private String type;
    @Column(name = "state")
    @Convert(converter = UserStateEnumConverter.class)
    private UserState state;
    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public void withdraw(){
        this.state = UserState.WITHDRAW;
        this.name = null;
        this.gender = null;
        this.phone = null;
        this.address = null;
        this.password = null;
        this.nickname = null;
        this.updatedAt = ZonedDateTime.now();
    }

    public UserDTO toDTO(){
        return UserDTO.builder()
                .id(id)
                .name(name)
                .gender(gender)
                .phone(phone)
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
