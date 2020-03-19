package com.iafoot.dbandcache.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserAndCar extends User{

    private List<Car> cars;
}
