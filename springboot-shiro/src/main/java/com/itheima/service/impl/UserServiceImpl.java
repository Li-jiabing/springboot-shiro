package com.itheima.service.impl;

import com.itheima.domain.User;

import com.itheima.mapper.UserMapper;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LiJiaBing
 * @date 3/12/2020 下午3:50
 */
@Service
public class UserServiceImpl  implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }
}
