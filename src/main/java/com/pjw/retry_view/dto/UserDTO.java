package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.User;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private Long id;
    private String name;
    private Integer gender;
    private String phone;
    private String address;
    private String loginId;
    private String password;
    private String nickname;
    private String type;
    private Integer state;

    private String createdBy;
    private Instant created;
    private String updatedBy;
    private Instant updated;

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
                .created(created)
                .updatedBy(updatedBy)
                .updated(updated)
                .build();
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", type='" + type + '\'' +
                ", state=" + state +
                ", createdBy='" + createdBy + '\'' +
                ", created=" + created +
                ", updatedBy='" + updatedBy + '\'' +
                ", updated=" + updated +
                '}';
    }
}
