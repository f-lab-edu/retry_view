package com.pjw.retry_view.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Long> getDeleteImageIds(List<Long> images, List<Long> oldImageIds){
        if(CollectionUtils.isEmpty(images) || CollectionUtils.isEmpty(oldImageIds)) return null;
        List<Long> deleteImageIds = new ArrayList<>();
        for(Long id : oldImageIds){
            if(!images.contains(id)){
                deleteImageIds.add(id);
            }
        }
        return deleteImageIds;
    }
}
