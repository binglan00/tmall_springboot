package com.chen.tmall.dao;

import com.chen.tmall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,Integer> {
    //注册时用的
    User findByName(String name);
    //登录时用的
    User getByNameAndPassword(String name, String password);

}
