package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.User;
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
    private String loginId;
    private String password;
    private String nickname;
    private String type;
    private Integer state;

    private String createdBy;
    private ZonedDateTime createdAt;
    private String updatedBy;
    private ZonedDateTime updatedAt;

    public User toEntity(){
        return User.builder()
                .id(id)
                .name(name)
                .gender(gender)
                .phone(phone)
                .address(address)
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .type(type)
                .state(state)
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
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", type='" + type + '\'' +
                ", state=" + state +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
