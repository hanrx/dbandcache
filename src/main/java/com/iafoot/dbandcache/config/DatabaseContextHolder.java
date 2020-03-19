package com.iafoot.dbandcache.config;

/**
 * 该类主要用于在选择数据源时，将相应的数据源的key设置到contextHolder中，之后对数据库的访问，就使用该key对应的数据源。
 */
public class DatabaseContextHolder {
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseType type){
        contextHolder.set(type);
    }

    public static DatabaseType getDatabaseType(){
        return contextHolder.get();
    }
}
