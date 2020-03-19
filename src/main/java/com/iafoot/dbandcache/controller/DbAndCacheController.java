package com.iafoot.dbandcache.controller;

import com.iafoot.dbandcache.model.User;
import com.iafoot.dbandcache.model.UserAndCar;
import com.iafoot.dbandcache.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("user相关api")
@RestController
@RequestMapping("/user")
public class DbAndCacheController {
    @Autowired
    private UserService userService;

    @ApiOperation("根据ID获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name ="id",dataType = "long",required = true,value = "用户的id",defaultValue = "1")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message = "权限校验不通过")
    })
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    public User getUserInfo(@RequestParam("id") long id){
        return userService.getUser(id);
    }

    @ApiOperation("根据用户ID获取用户及其车辆信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "id",dataType = "long",required = true,value = "用户的id",defaultValue = "1")
    })
    @RequestMapping(value = "/getUserAndCar",method = RequestMethod.GET)
    public UserAndCar getUserAndCar(@RequestParam("id") long id){
        return userService.getUserAndCar(id);
    }
}














