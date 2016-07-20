package com.xxx.sample.shiro.service.client;

import com.xxx.sample.shiro.service.remote.RemoteServiceInterface;
import com.xxx.sample.shiro.service.utils.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import java.io.Serializable;

/**
 * <p>User: Zhang aolin
 * <p>Date: 14-3-13
 * <p>Version: 1.0
 */
public class ClientSessionDAO extends CachingSessionDAO {

    private RemoteServiceInterface remoteService;
    private String appKey;

    public void setRemoteService(RemoteServiceInterface remoteService) {
        this.remoteService = remoteService;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }


    @Override
    protected void doDelete(Session session) {
        remoteService.deleteSession(appKey, SerializableUtils.serialize(session));
    }

    @Override
    protected void doUpdate(Session session) {
        remoteService.updateSession(appKey, SerializableUtils.serialize(session));
    }


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = remoteService.createSession(SerializableUtils.serialize(session));
        assignSessionId(session, sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = SerializableUtils.deserialize(remoteService.getSession(appKey, sessionId));
        return session;
    }
}
