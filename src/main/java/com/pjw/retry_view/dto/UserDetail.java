package com.pjw.retry_view.dto;

import com.pjw.retry_view.enums.UserAuth;
import com.pjw.retry_view.enums.UserState;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetail implements UserDetails {
    private Long id;
    private String name;
    private UserAuth role;
    private String loginId;
    private String password;
    private UserState state;

    @Builder
    public UserDetail(Long id, String name, UserAuth role, String loginId, String password, UserState state) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.loginId = loginId;
        this.password = password;
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", state=" + state +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) () -> role.getCode());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    public Long getId(){
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return org.springframework.security.core.userdetails.UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return org.springframework.security.core.userdetails.UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return org.springframework.security.core.userdetails.UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return org.springframework.security.core.userdetails.UserDetails.super.isEnabled();
    }
}
