package com.pjw.retry_view.converter;

import com.pjw.retry_view.dto.BoardType;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;

public class BoardTypeEnumConverter implements AttributeConverter<BoardType, String> {
    @Override
    public String convertToDatabaseColumn(BoardType boardType) {
        if(boardType == null) return BoardType.SELL.getCode();
        return boardType.getCode();
    }

    @Override
    public BoardType convertToEntityAttribute(String code) {
        if(StringUtils.isBlank(code)) return BoardType.SELL;
        return BoardType.getValue(code);
    }

}
