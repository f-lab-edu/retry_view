package com.pjw.retry_view.validator;

import com.pjw.retry_view.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Set;
@Component
public class UserValidator implements Validator {

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO user = (UserDTO)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required","이름은 필수 입력값입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "field.required","연락처는 필수 입력값입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "field.required","주소는 필수 입력값입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginId", "field.required","로그인 아이디는 필수 입력값입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required","비밀번호는 필수 입력값입니다.");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return (UserDTO.class.isAssignableFrom(clazz));
    }
}
