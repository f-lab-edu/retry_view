package com.pjw.retry_view.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private Long id;
    private String name;
    private String loginId;
    private UserAuth role;

    public UserInfo(){}
    public UserInfo(Long id, String name, String loginId, UserAuth auth){
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.role = auth;
    }
}
