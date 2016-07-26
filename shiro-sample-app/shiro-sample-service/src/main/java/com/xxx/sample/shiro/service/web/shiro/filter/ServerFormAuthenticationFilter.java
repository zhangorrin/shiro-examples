package com.xxx.sample.shiro.service.web.shiro.filter;

import com.xxx.sample.shiro.service.remote.SysToken;
import com.xxx.sample.shiro.service.service.SysTokenService;
import com.xxx.sample.shiro.service.utils.date.DateUtil;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * <p>User: Zhang aolin
 * <p>Date: 14-3-16
 * <p>Version: 1.0
 */
public class ServerFormAuthenticationFilter extends FormAuthenticationFilter {

    @Autowired
    private SysTokenService sysTokenService;

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        String fallbackUrl = (String) getSubject(request, response)
                .getSession().getAttribute("authc.fallbackUrl");
        if(StringUtils.isEmpty(fallbackUrl)) {
            fallbackUrl = getSuccessUrl();
        }

        //增加sys_token
        String token = UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
        SysToken sysToken = new SysToken();
        sysToken.setUsername(getSubject(request, response).getPrincipal().toString());
        Date tokenCreateDate = new Date();
        sysToken.setCreate_time(tokenCreateDate);
        sysToken.setDie_time(DateUtil.addSecond(tokenCreateDate,30));
        sysToken.setToken(token);
        sysToken.setApp_id(Long.valueOf(0));
        sysTokenService.createSysToken(sysToken);


        if(fallbackUrl.contains("?")){
            fallbackUrl = fallbackUrl + "&token=" + token;
        }else {
            fallbackUrl = fallbackUrl + "?token=" + token;
        }

        WebUtils.issueRedirect(request,response,fallbackUrl);

    }

}
