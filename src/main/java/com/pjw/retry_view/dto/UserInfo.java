package com.pjw.retry_view.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String name;
    private String loginId;

    public UserInfo(){}
    public UserInfo(String name, String loginId){
        this.name = name;
        this.loginId = loginId;
    }
}
