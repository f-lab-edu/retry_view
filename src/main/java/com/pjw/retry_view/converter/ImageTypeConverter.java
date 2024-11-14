package com.pjw.retry_view.converter;

import com.pjw.retry_view.enums.ImageType;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;

public class ImageTypeConverter implements AttributeConverter<ImageType, String> {
    @Override
    public String convertToDatabaseColumn(ImageType imageType) {
        if(imageType == null) return ImageType.BOARD.getCode();
        return imageType.getCode();
    }

    @Override
    public ImageType convertToEntityAttribute(String code) {
        if(StringUtils.isBlank(code)) return ImageType.BOARD;
        return ImageType.getValue(code);
    }
}
