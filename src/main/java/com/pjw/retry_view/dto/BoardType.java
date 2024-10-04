package com.pjw.retry_view.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BoardType {
    SELL("Sell"),
    BUY("Buy"),
    FREE_SHARE("FreeShare");
    @JsonValue
    private final String code;
    BoardType(String code){ this.code = code; }

    @JsonCreator
    public static BoardType getValue(String code){
        if(StringUtils.isBlank(code)) return BoardType.SELL;
        return Arrays.stream(BoardType.values()).filter((type) -> type.getCode().equals(code)).findFirst().orElse(BoardType.SELL);
    }
}
