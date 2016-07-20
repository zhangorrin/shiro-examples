/**
 * Copyright (c) 2005-2012 https://github.com/zhangaolin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.xxx.sample.shiro.service.remote;

import java.io.Serializable;

/**
 * <p>User: Zhang aolin
 * <p>Date: 14-3-13
 * <p>Version: 1.0
 */
public interface RemoteServiceInterface {

    public String getSession(String appKey, Serializable sessionId);
    Serializable createSession(String sessionSerializableString);
    public void updateSession(String appKey, String sessionSerializableString);
    public void deleteSession(String appKey, String sessionSerializableString);

    public PermissionContext getPermissions(String appKey, String username);
}
