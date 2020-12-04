package com.itheima.mapper;

import com.itheima.domain.User;

/**
 * @author LiJiaBing
 * @date 3/12/2020 下午3:31
 */
public interface UserMapper {

     User findByName(String name);

     User findById(Integer id);
}
