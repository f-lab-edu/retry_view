package com.pjw.retry_view.converter;

import com.pjw.retry_view.enums.SearchType;
import org.springframework.core.convert.converter.Converter;

public class SearchTypeConverter implements Converter<String, SearchType> {
    @Override
    public SearchType convert(String source) {
        return SearchType.getValue(source);
    }
}
