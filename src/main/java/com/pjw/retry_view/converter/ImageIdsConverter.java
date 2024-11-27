package com.pjw.retry_view.converter;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ImageIdsConverter implements AttributeConverter<List<Long>, String> {
    @Override
    public String convertToDatabaseColumn(List<Long> imageIds) {
        if(CollectionUtils.isEmpty(imageIds)) return "";
        return String.join(",",imageIds.stream().map(String::valueOf).collect(Collectors.toSet()));
    }

    @Override
    public List<Long> convertToEntityAttribute(String imageIds) {
        List<Long> result = new ArrayList<>();
        if(StringUtils.isBlank(imageIds)) return result;

        StringTokenizer token = new StringTokenizer(imageIds, ",");
        while(token.hasMoreElements()){
            result.add(Long.parseLong(token.nextToken()));
        }
        return result;
    }
}
