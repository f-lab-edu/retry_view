package com.pjw.retry_view.converter;

import com.pjw.retry_view.dto.UserState;
import jakarta.persistence.AttributeConverter;
import org.springframework.core.convert.converter.Converter;

public class UserStateEnumConverter implements AttributeConverter<UserState, Integer> , Converter<Integer, UserState> {
    @Override
    public Integer convertToDatabaseColumn(UserState userStete) {
        if(userStete == null) return null;
        System.out.println(userStete);
        return userStete.getCode();
    }

    @Override
    public UserState convertToEntityAttribute(Integer code) {
        if(code == null) return UserState.NORMAL;
        System.out.println(code);
        return UserState.getValue(code);
    }

    @Override
    public UserState convert(Integer source) {
        return UserState.getValue(source);
    }
}
