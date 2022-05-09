package com.ctgu.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.reader.entity.User;
import com.ctgu.reader.mapper.UserMapper;
import com.ctgu.reader.service.UserService;
import com.ctgu.reader.service.exception.BusinessException;
import com.ctgu.reader.utils.Md5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Boliang Weng
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 返回用户实体类
     */
    @Override
    public User selectByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回登录成功的用户
     */
    @Override
    public User checkLogin(String username, String password) {
        User user = selectByUsername(username);
        if (user == null) {
            throw new BusinessException("U01", "用户名不存在");
        }
        Integer salt = user.getSalt();
        String md5Digest = Md5Utils.getMd5Digest(password, salt);

        if (!user.getPassword().equals(md5Digest)) {
            throw new BusinessException("U02", "密码错误");
        }

        return user;
    }
}
