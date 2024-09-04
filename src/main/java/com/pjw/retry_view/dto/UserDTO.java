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
    private String loginId;
    private String password;
    private Instant created;
    private Instant updated;
/*
    public UserDTO(Long id, String name, String loginId, String password, Instant created, Instant updated){
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.created = created;
        this.updated = updated;
    }
*/
    public User toEntity(){
        return User.builder()
                .id(id)
                .name(name)
                .loginId(loginId)
                .password(password)
                .created(created)
                .updated(updated)
                .build();
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
