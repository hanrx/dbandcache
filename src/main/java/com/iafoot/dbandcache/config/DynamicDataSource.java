package com.iafoot.dbandcache.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 该类实例了继承自AbstractRoutingDataSource的determineCurrentLookupKey()方法，在该方法中**通过调用数据源key持有类DatabaseContextHolder.getDataBaseType()方法来获取数据源key**。
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    protected Object determineCurrentLookupKey(){
        return DatabaseContextHolder.getDatabaseType();
    }
}
