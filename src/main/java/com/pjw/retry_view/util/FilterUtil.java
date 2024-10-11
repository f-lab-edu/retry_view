package com.pjw.retry_view.util;

import java.util.HashSet;
import java.util.Set;

public class FilterUtil {
    public static final Set<String> excludeUrlPatterns = new HashSet<>(Set.of("/login","/users/regist","/admin/regist"));
    public static final String ADMIN_URL = "/admin";
}
