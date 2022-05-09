package com.ctgu.reader.service;

import com.ctgu.reader.entity.User;

/**
 * @author Boliang Weng
 */
public interface UserService {
    /**
     *  通过用户名查询用户信息
     * @param username 用户名
     * @return 返回用户实体类
     */
    public User selectByUsername(String username);

    /**
     *  登录验证
     * @param username 用户名
     * @param password 密码
     * @return 返回登录成功的用户
     */
    public User checkLogin(String username, String password);
}
