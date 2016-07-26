package com.xxx.sample.shiro.service.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public static String getDomainName(String url)
	{

		Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		matcher.find();
		return matcher.group();
	}

	public static  String getDefaultBackUrl(HttpServletRequest request) {
		String scheme = request.getScheme();
		String domain = request.getServerName();
		int port = request.getServerPort();
		String contextPath = request.getContextPath();
		StringBuilder backUrl = new StringBuilder(scheme);
		backUrl.append("://");
		backUrl.append(domain);
		if("http".equalsIgnoreCase(scheme) && port != 80) {
			backUrl.append(":").append(String.valueOf(port));
		} else if("https".equalsIgnoreCase(scheme) && port != 443) {
			backUrl.append(":").append(String.valueOf(port));
		}
		backUrl.append(contextPath);
		return backUrl.toString();
	}
}
