package com.xxx.sample.shiro.service.web.shiro.filter;

import com.xxx.sample.shiro.service.remote.SysToken;
import com.xxx.sample.shiro.service.service.SysTokenService;
import com.xxx.sample.shiro.service.utils.date.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Orrin on 2016/7/25.
 */
public class ServerAdviceFilter extends AdviceFilter {

	@Autowired
	private SysTokenService sysTokenService;

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {


		System.out.println("***ServerAdviceFilter.preHandle***");
		Subject subject = SecurityUtils.getSubject();
		System.out.println(subject.isAuthenticated());
		String fromURL = request.getParameter("fromURL");
		System.out.println("****************fromURL:"+fromURL);
		System.out.println("********************************");

		if(!subject.isAuthenticated() && !StringUtils.isEmpty(fromURL)){
			subject.getSession().setAttribute("authc.fallbackUrl", fromURL);
		}


		if(subject.isAuthenticated() && !StringUtils.isEmpty(fromURL)){

			//增加sys_token
			String token = UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
			SysToken sysToken = new SysToken();
			sysToken.setUsername(subject.getPrincipal().toString());
			Date tokenCreateDate = new Date();
			sysToken.setCreate_time(tokenCreateDate);
			sysToken.setDie_time(DateUtil.addSecond(tokenCreateDate,30));
			sysToken.setToken(token);
			sysToken.setApp_id(Long.valueOf(0));
			sysTokenService.createSysToken(sysToken);

			if(fromURL.contains("?token=")){
				WebUtils.issueRedirect(request, response, fromURL);
			}else{
				WebUtils.issueRedirect(request, response, fromURL+"?token="+token);
			}
		}

		return super.preHandle(request, response);
	}
}
