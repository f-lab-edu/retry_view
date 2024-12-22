package com.pjw.retry_view.converter;

import com.pjw.retry_view.enums.CategoryType;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;
import org.springframework.core.convert.converter.Converter;

public class CategoryTypeEnumConverter implements AttributeConverter<CategoryType, String> , Converter<String, CategoryType> {
    @Override
    public String convertToDatabaseColumn(CategoryType categoryType) {
        if(categoryType == null) return CategoryType.MAIN.getCode();
        return categoryType.getCode();
    }

    @Override
    public CategoryType convertToEntityAttribute(String code) {
        if(StringUtils.isBlank(code)) return CategoryType.MAIN;
        return CategoryType.getValue(code);
    }

    @Override
    public CategoryType convert(String source) {
        return CategoryType.getValue(source);
    }
}
