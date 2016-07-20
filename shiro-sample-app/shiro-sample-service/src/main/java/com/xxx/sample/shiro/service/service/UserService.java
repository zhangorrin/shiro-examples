package com.xxx.sample.shiro.service.service;

import com.xxx.sample.shiro.service.entity.User;

import java.util.List;

/**
 * <p>User: Zhang aolin
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface UserService {

    /**
     * 创建用户
     * @param user
     */
    public User createUser(User user);

    public User updateUser(User user);

    public void deleteUser(Long userId);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword);


    User findOne(Long userId);

    List<User> findAll();

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username);


}
