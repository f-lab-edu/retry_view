package com.pjw.retry_view.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String name;
    private String loginId;
    private UserAuth role;

    public UserInfo(){}
    public UserInfo(String name, String loginId, UserAuth auth){
        this.name = name;
        this.loginId = loginId;
        this.role = auth;
    }
}
