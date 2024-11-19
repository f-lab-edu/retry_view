package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.User;
import com.pjw.retry_view.entity.UserId;
import com.pjw.retry_view.enums.Gender;
import com.pjw.retry_view.enums.UserAuth;
import com.pjw.retry_view.enums.UserState;
import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private Long id;
    private String name;
    private Gender gender;
    private String phone;
    private String address;
    private UserAuth role;
    private String loginId;
    private String password;
    private String nickname;
    private String type;
    private UserState state;
    private String refreshToken;

    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private ZonedDateTime updatedAt;

    public void changeRefereshToken(String token){
        this.refreshToken = token;
        this.updatedAt = ZonedDateTime.now();
    }

    public User toEntity(){
        return User.builder()
                .userId(UserId.of(id))
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", type='" + type + '\'' +
                ", state=" + state +
                ", refreshToken='" + refreshToken + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
