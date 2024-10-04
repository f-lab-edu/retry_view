package com.pjw.retry_view.converter;

import com.pjw.retry_view.dto.UserState;
import jakarta.persistence.AttributeConverter;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public class UserStateEnumConverter implements AttributeConverter<UserState, Integer> , Converter<Integer, UserState> {
    @Override
    public Integer convertToDatabaseColumn(UserState userState) {
        return Optional.ofNullable(userState).map(UserState::getCode).orElse(UserState.NORMAL.getCode());
    }

    @Override
    public UserState convertToEntityAttribute(Integer code) {
        return Optional.ofNullable(code).map(UserState::getValue).orElse(UserState.NORMAL);
    }

    @Override
    public UserState convert(Integer source) {
        return UserState.getValue(source);
    }
}
