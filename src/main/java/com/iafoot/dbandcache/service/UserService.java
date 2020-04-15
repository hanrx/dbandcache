package com.iafoot.dbandcache.service;

import com.alibaba.fastjson.JSON;
import com.iafoot.dbandcache.common.DbAndCacheContants;
import com.iafoot.dbandcache.dao.CarDao;
import com.iafoot.dbandcache.dao.UserDao;
import com.iafoot.dbandcache.model.Car;
import com.iafoot.dbandcache.model.User;
import com.iafoot.dbandcache.model.UserAndCar;
import com.iafoot.dbandcache.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CarDao carDao;
    @Autowired
    private RedisUtil redisUtil;
    public User getUserCache(long id){
        String userStr = redisUtil.get(DbAndCacheContants.USER_CACHE_PREFIX + id);
        if (StringUtils.isNotBlank(userStr)){
            return JSON.parseObject(userStr,User.class);
        }
        User user = userDao.selectByPrimaryKey(id);
        if (user != null){
            redisUtil.set(DbAndCacheContants.USER_CACHE_PREFIX+id,JSON.toJSONString(user));
        }
        return user;
    }
    public User getUser(long id){
        return userDao.selectByPrimaryKey(id);
    }

    public UserAndCar getUserAndCar(long userId){
        UserAndCar userAndCar = new UserAndCar();
        User user = userDao.selectByPrimaryKey(userId);
        if(user != null){
            List<Car> cars = carDao.selectByOwner(userId);
            userAndCar.setId(user.getId());
            userAndCar.setName(user.getName());
            userAndCar.setPhone(user.getPhone());
            userAndCar.setCars(cars);
        }
        return userAndCar;
    }
}
