package com.itheima.service;

import com.itheima.domain.User;

/**
 * @author LiJiaBing
 * @date 3/12/2020 下午3:48
 */
public interface UserService {

     User findByName(String name);

     User findById(Integer id);
}
