package com.pjw.retry_view.util;

import io.micrometer.common.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class FilterUtil {
    protected static final Set<String> excludeUrlPatterns = new HashSet<>(Set.of("/login","/users/regist","/admin/regist","/swagger-ui","/favicon.ico","/v3/api-docs","/PushTest.html"));
    public static final String ADMIN_URL = "/admin";

    public static boolean isExcludeUrlPatterns(String uri){
        if(StringUtils.isBlank(uri)) return true;
        return excludeUrlPatterns.stream().anyMatch(uri::startsWith);
    }
}
