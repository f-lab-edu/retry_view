package com.pjw.retry_view.converter;

import com.pjw.retry_view.dto.UserAuth;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;

import java.util.Optional;

public class UserAutnEnumConverter implements AttributeConverter<UserAuth, String> {
    @Override
    public String convertToDatabaseColumn(UserAuth userAuth) {
        return Optional.ofNullable(userAuth).map(UserAuth::getCode).orElse(UserAuth.USER.getCode());
    }

    @Override
    public UserAuth convertToEntityAttribute(String code) {
        if(StringUtils.isBlank(code)) return UserAuth.USER;
        return UserAuth.getValue(code);
    }
}
