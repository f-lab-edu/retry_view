package com.pjw.retry_view.converter;

import com.pjw.retry_view.enums.DeviceType;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;

public class DeviceTypeConverter implements AttributeConverter<DeviceType, String> {
    @Override
    public String convertToDatabaseColumn(DeviceType deviceType) {
        if(deviceType == null) return DeviceType.OTHER.getCode();
        return deviceType.getCode();
    }

    @Override
    public DeviceType convertToEntityAttribute(String code) {
        if(StringUtils.isBlank(code)) return DeviceType.OTHER;
        return DeviceType.getValue(code);
    }
}
