package com.xxx.sample.shiro.service.service;

import com.xxx.sample.shiro.service.remote.SysToken;

/**
 * <p>User: Zhang aolin
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface SysTokenService {

    public SysToken createSysToken(SysToken sysToken);


    public SysToken findByToken(String token);

}
