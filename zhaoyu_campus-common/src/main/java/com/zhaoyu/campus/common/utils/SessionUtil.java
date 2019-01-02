package com.zhaoyu.campus.common.utils;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
	
	public static Object getAttribute(HttpServletRequest request, String key) {
		return request.getSession().getAttribute(key);
	}

	public static void setAttribute(HttpServletRequest request, String key,
			Object value) {
		request.getSession().setAttribute(key, value);
	}
}
