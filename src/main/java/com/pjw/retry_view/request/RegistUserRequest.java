package com.pjw.retry_view.request;

import com.pjw.retry_view.dto.Gender;
import com.pjw.retry_view.dto.UserDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistUserRequest {
    @NotNull(message = "이름은 필수 입력값입니다.")
    private String name;
    @NotNull(message = "성별은 필수 입력값입니다.")
    private Gender gender;
    @NotNull(message = "연락처 필수 입력값입니다.")
    private String phone;
    @NotNull(message = "주소는 필수 입력값입니다.")
    private String address;
    @NotNull(message = "아이디는 필수 입력값입니다.")
    private String loginId;
    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    private String nickname;

    public UserDTO toUserDTO(){
        return UserDTO.builder()
                .name(name)
                .gender(gender)
                .phone(phone)
                .address(address)
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
