package com.pjw.retry_view.entity;

import com.pjw.retry_view.dto.Gender;
import com.pjw.retry_view.dto.UserDTO;
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
    private String role;
    @Column(name = "login_id")
    private String loginId;
    @Column(name = "password")
    private String password;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "type")
    private String type;
    @Column(name = "state")
    private Integer state;
    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

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
