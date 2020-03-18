package com.iafoot.dbandcache.service;

import com.iafoot.dbandcache.dao.UserDao;
import com.iafoot.dbandcache.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public User getUser(long id){
        return userDao.selectByPrimaryKey(id);
    }
}
