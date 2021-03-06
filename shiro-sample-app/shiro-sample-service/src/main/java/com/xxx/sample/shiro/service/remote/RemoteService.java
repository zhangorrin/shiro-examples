package com.xxx.sample.shiro.service.remote;

import com.alibaba.dubbo.config.annotation.Service;
import com.xxx.sample.shiro.service.service.AuthorizationService;
import com.xxx.sample.shiro.service.service.SysTokenService;
import com.xxx.sample.shiro.service.service.UserService;
import com.xxx.sample.shiro.service.utils.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * <p>User: Zhang aolin
 * <p>Date: 14-3-13
 * <p>Version: 1.0
 */
@Service
public class RemoteService implements RemoteServiceInterface {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private SysTokenService sysTokenService;

    @Override
    public String getSession(String appKey, Serializable sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        return SerializableUtils.serialize(session);
    }

    @Override
    public Serializable createSession(String sessionSerializableString) {
        return sessionDAO.create(SerializableUtils.deserialize(sessionSerializableString));
    }

    @Override
    public void updateSession(String appKey, String sessionSerializableString) {
        sessionDAO.update(SerializableUtils.deserialize(sessionSerializableString));
    }

    @Override
    public void deleteSession(String appKey, String sessionSerializableString) {
        sessionDAO.delete(SerializableUtils.deserialize(sessionSerializableString));
    }

    @Override
    public PermissionContext getPermissions(String appKey, String username) {
        PermissionContext permissionContext = new PermissionContext();
        permissionContext.setRoles(authorizationService.findRoles(appKey, username));
        permissionContext.setPermissions(authorizationService.findPermissions(appKey, username));
        return permissionContext;
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username){
        return userService.findByUsername(username);
    }

    /**
     * 查找跨域名token
     * @param token
     * @return
     */
    @Override
    public SysToken findByToken(String token) {
        return sysTokenService.findByToken(token);
    }
}
