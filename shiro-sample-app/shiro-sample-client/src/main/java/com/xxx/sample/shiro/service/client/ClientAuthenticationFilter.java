package com.xxx.sample.shiro.service.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xxx.sample.shiro.service.core.ClientSavedRequest;
import com.xxx.sample.shiro.service.remote.RemoteServiceInterface;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>User: Zhang aolin
 * <p>Date: 14-3-14
 * <p>Version: 1.0
 */
public class ClientAuthenticationFilter extends AuthenticationFilter {

    private String loginUrlSet;

    @Reference
    private RemoteServiceInterface remoteService;

    public void setLoginUrlSet(String loginUrlSet) {
        this.loginUrlSet = loginUrlSet;
    }

    public RemoteServiceInterface getRemoteService() {
        return remoteService;
    }

    public void setRemoteService(RemoteServiceInterface remoteService) {
        this.remoteService = remoteService;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);

        String token = request.getParameter("token");

        System.out.println("ClientAuthenticationFilter****isAccessAllowed***request url : "+WebUtils.toHttp(request).getRequestURL());

        if(!subject.isAuthenticated() && !StringUtils.isEmpty(token)){

            //token中带有登录用户
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken();
            usernamePasswordToken.setUsername(token);
            usernamePasswordToken.setPassword(token.toCharArray());
            /*SysToken sysToken = remoteService.findByToken(token);

            User user = remoteService.findByUsername(sysToken.getUsername());

            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken();
            usernamePasswordToken.setUsername(user.getUsername());
            usernamePasswordToken.setPassword(user.getPassword().toCharArray());*/


            subject.login(usernamePasswordToken);

        }



        return subject.isAuthenticated();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String backUrl = request.getParameter("backUrl");
        saveRequest(request, backUrl, getDefaultBackUrl(WebUtils.toHttp(request)));

        System.out.println("***loginUrl***");
        String loginUrlTemp = this.loginUrlSet+"?fromURL="+new ClientSavedRequest(WebUtils.toHttp(request), backUrl).getRequestUrl();
        System.out.println(loginUrlTemp);
        System.out.println("***loginUrl***");
        this.setLoginUrl(loginUrlTemp);

        redirectToLogin(request, response);

        return false;
    }
    protected void saveRequest(ServletRequest request, String backUrl, String fallbackUrl) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        session.setAttribute("authc.fallbackUrl", fallbackUrl);
        SavedRequest savedRequest = new ClientSavedRequest(httpRequest, backUrl);
        System.out.println("******:"+savedRequest.getRequestUrl());
        session.setAttribute(WebUtils.SAVED_REQUEST_KEY, savedRequest);
    }
    private String getDefaultBackUrl(HttpServletRequest request) {
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
        backUrl.append(getSuccessUrl());
        return backUrl.toString();
    }

}
