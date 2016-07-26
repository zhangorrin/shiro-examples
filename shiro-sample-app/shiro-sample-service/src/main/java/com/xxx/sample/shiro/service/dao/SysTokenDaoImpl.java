package com.xxx.sample.shiro.service.dao;

import com.xxx.sample.shiro.service.remote.SysToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * <p>User: Zhang aolin
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Repository
public class SysTokenDaoImpl implements SysTokenDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public SysToken createSysToken(final SysToken sysToken) {
        final String sql = "insert into sys_token (app_id, username, token, die_time) values(?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setLong(count++, sysToken.getApp_id());
                psst.setString(count++, sysToken.getUsername());
                psst.setString(count++, sysToken.getToken());
                psst.setTimestamp(count++, new java.sql.Timestamp(sysToken.getDie_time().getTime()));
                return psst;
            }
        }, keyHolder);

        sysToken.setId(keyHolder.getKey().longValue());
        return sysToken;
    }

    @Override
    public SysToken findByToken(String token) {
        String sql = "select id, app_id, username, token, create_time, die_time from sys_token where token=?";
        List<SysToken> sysTokenList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SysToken.class), token);

        if(sysTokenList.size() == 0) {
            return null;
        }
        return sysTokenList.get(0);
    }
}
