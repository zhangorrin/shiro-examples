package com.xxx.sample.shiro.service.remote;

import com.xxx.sample.shiro.service.utils.date.DateStyle;
import com.xxx.sample.shiro.service.utils.date.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>User: Zhang aolin
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class SysToken implements Serializable {
    private Long id; //编号
    private Long app_id; //应用号
    private String username; //用户名
    private String token; //token
    private Date create_time = new Date(); //创建时间
    private Date die_time;

    public SysToken() {
    }

    public SysToken(Long app_id, String username,String token) {
        this.app_id = app_id;
        this.username = username;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApp_id() {
        return app_id;
    }

    public void setApp_id(Long app_id) {
        this.app_id = app_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getDie_time() {
        return die_time;
    }

    public void setDie_time(Date die_time) {
        this.die_time = die_time;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SysToken{" +
                "id=" + id +
                ", app_id=" + app_id +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", create_time='" + DateUtil.DateToString(create_time, DateStyle.YYYYMMDDHHMMSSSSSS) + '\'' +
                ", die_time=" + DateUtil.DateToString(die_time, DateStyle.YYYYMMDDHHMMSSSSSS) +
                '}';
    }
}
