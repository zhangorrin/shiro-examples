package com.xxx.sample.shiro.service.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Orrin on 2016/7/20.
 */
public class RequestUtils {
	public static javax.servlet.http.Cookie getCookie(HttpServletRequest request, String cookieName) {
		javax.servlet.http.Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			javax.servlet.http.Cookie[] var3 = cookies;
			int var4 = cookies.length;

			for(int var5 = 0; var5 < var4; ++var5) {
				javax.servlet.http.Cookie cookie = var3[var5];
				if(cookie.getName().equals(cookieName)) {
					return cookie;
				}
			}
		}

		return null;
	}
}
