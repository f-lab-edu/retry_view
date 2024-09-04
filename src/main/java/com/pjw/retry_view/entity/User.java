package com.pjw.retry_view.entity;

import com.pjw.retry_view.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User {
    @Id
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "LOGIN_ID")
    private String loginId;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "CREATED")
    private Instant created;
    @Column(name = "UPDATED")
    private Instant updated;

    @Builder
    public User(Long id, String name, String loginId, String password, Instant created, Instant updated){
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.created = created;
        this.updated = updated;
    }

    public UserDTO toDTO(){
        return UserDTO.builder()
                .id(id)
                .name(name)
                .loginId(loginId)
                .password(password)
                .created(created)
                .updated(updated)
                .build();
    }
}
