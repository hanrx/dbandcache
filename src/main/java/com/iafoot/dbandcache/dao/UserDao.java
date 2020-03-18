package com.iafoot.dbandcache.dao;

import com.iafoot.dbandcache.mapper.UserMapper;
import com.iafoot.dbandcache.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    private UserMapper userMapper;
    public User selectByPrimaryKey(long id){
        return userMapper.selectByPrimaryKey(id);
    }
}
