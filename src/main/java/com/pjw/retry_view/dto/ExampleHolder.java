package com.pjw.retry_view.dto;

import io.swagger.v3.oas.models.examples.Example;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExampleHolder {
    private Example holder;
    private int code;
    private String name;

    @Builder
    public ExampleHolder(Example holder, int code, String name) {
        this.holder = holder;
        this.code = code;
        this.name = name;
    }

}
