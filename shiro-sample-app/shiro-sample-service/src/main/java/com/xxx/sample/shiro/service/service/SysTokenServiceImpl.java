package com.xxx.sample.shiro.service.service;

import com.xxx.sample.shiro.service.dao.SysTokenDao;
import com.xxx.sample.shiro.service.remote.SysToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>User: Zhang aolin
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Service("sysTokenService")
public class SysTokenServiceImpl implements SysTokenService {

    @Autowired
    private SysTokenDao sysTokenDao;


    @Override
    public SysToken createSysToken(SysToken sysToken) {
        return sysTokenDao.createSysToken(sysToken);
    }

    @Override
    public SysToken findByToken(String token) {
        return sysTokenDao.findByToken(token);
    }
}
