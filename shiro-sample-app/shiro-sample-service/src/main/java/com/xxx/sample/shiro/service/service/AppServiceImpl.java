package com.xxx.sample.shiro.service.service;

import com.xxx.sample.shiro.service.dao.AppDao;
import com.xxx.sample.shiro.service.entity.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>User: Zhang aolin
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Service

public class AppServiceImpl implements AppService {

    @Autowired
    private AppDao appDao;

    public App createApp(App app) {
        return appDao.createApp(app);
    }

    public App updateApp(App app) {
        return appDao.updateApp(app);
    }

    public void deleteApp(Long appId) {
        appDao.deleteApp(appId);
    }

    @Override
    public App findOne(Long appId) {
        return appDao.findOne(appId);
    }

    @Override
    public List<App> findAll() {
        return appDao.findAll();
    }

    @Override
    public Long findAppIdByAppKey(String appKey) {
        return appDao.findAppIdByAppKey(appKey);
    }
}
