package com.pjw.retry_view.util;

import java.util.HashSet;
import java.util.Set;

public class FilterUtil {
    public static final Set<String> excludeUrlPatterns = new HashSet<>(Set.of("/login","/users/regist","/admin/regist","/ws","/chat/msg"));
    public static final String ADMIN_URL = "/admin";

    public static boolean isExcludeUrlPatterns(String uri){
        return excludeUrlPatterns.contains(uri);
    }
}
