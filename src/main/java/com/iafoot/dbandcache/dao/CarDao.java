package com.iafoot.dbandcache.dao;

import com.iafoot.dbandcache.config.DatabaseContextHolder;
import com.iafoot.dbandcache.config.DatabaseType;
import com.iafoot.dbandcache.mapper.CarMapper;
import com.iafoot.dbandcache.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarDao {
@Autowired
    private CarMapper carMapper;
    public List<Car> selectByOwner(long ownerId){
        DatabaseContextHolder.setDatabaseType(DatabaseType.microservicedb2);
        return carMapper.selectByOwner(ownerId);
    }
}
