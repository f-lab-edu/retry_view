package com.pjw.retry_view.converter;

import com.pjw.retry_view.dto.UserAuth;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;

public class UserAutnEnumConverter implements AttributeConverter<UserAuth, String> {
    @Override
    public String convertToDatabaseColumn(UserAuth userAuth) {
        if(userAuth == null) return UserAuth.USER.getCode();
        return userAuth.getCode();
    }

    @Override
    public UserAuth convertToEntityAttribute(String code) {
        if(StringUtils.isBlank(code)) return UserAuth.USER;
        return UserAuth.getValue(code);
    }
}
