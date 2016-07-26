package com.xxx.sample.shiro.service.client;

import com.xxx.sample.shiro.service.remote.PermissionContext;
import com.xxx.sample.shiro.service.remote.RemoteServiceInterface;
import com.xxx.sample.shiro.service.remote.SysToken;
import com.xxx.sample.shiro.service.utils.date.DateStyle;
import com.xxx.sample.shiro.service.utils.date.DateUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Date;

/**
 * <p>User: Zhang aolin
 * <p>Date: 14-3-13
 * <p>Version: 1.0
 */
public class ClientRealm extends AuthorizingRealm {
    private RemoteServiceInterface remoteService;
    private String appKey;
    public void setRemoteService(RemoteServiceInterface remoteService) {
        this.remoteService = remoteService;
    }
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    //授权信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        PermissionContext context = remoteService.getPermissions(appKey, username);
        authorizationInfo.setRoles(context.getRoles());
        authorizationInfo.setStringPermissions(context.getPermissions());
        return authorizationInfo;
    }

    //获取身份验证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //永远不会被调用
        //throw new UnsupportedOperationException("永远不会被调用");
        String castoken = (String)token.getPrincipal();

        SysToken sysToken = remoteService.findByToken(castoken);

        if(sysToken == null) {
            throw new UnknownAccountException();//没找到帐号
        }


        System.out.println("*******ClientRealm过期校验new Date()："+ DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN));
        System.out.println("*******ClientRealm过期校验Die_time："+DateUtil.DateToString(sysToken.getDie_time(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN));
        System.out.println("*******ClientRealm过期校验结果："+new Date().after(sysToken.getDie_time()));

        if(new Date().after(sysToken.getDie_time())) {
            throw new AccountException("凭证过期"); //过期
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                sysToken.getUsername(), //用户名
                sysToken.getToken(), //密码
                ByteSource.Util.bytes(sysToken.getToken()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
