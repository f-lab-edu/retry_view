package com.pjw.retry_view.entity;

import com.pjw.retry_view.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

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
    @Column(name = "NAME")
    private String name;
    @Column(name = "GENDER")
    private Integer gender;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "LOGIN_ID")
    private String loginId;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "NICKNAME")
    private String nickname;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "STATE")
    private Integer state;


    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED")
    private Instant created;
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    @Column(name = "UPDATED")
    private Instant updated;

    public UserDTO toDTO(){
        return UserDTO.builder()
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
}
