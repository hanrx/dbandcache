package com.iafoot.dbandcache.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 该类是操作缓存的工具类，这里笔者只写了两个操作String类型的方法：set和get，
 * 可以自己将其他数据结构的方法封装在该类中。看一下set和get的逻辑，首先从ShardedJedisPool中获取shardedJedis，可以将此理解为操作Redis的一个连接，
 * 之后使用该连接进行数据库的操作。最后，无论操作成功与否，都要将shardedJedis资源关闭。
 */
@Component
public class RedisUtil {

    @Autowired
    private ShardedJedisPool pool;

    public void set(String key, String value){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = pool.getResource();
            if (shardedJedis != null){
                shardedJedis.set(key,value);
            }
        } catch (Exception e) {

        }finally {
            if (shardedJedis != null){
                shardedJedis.close();
            }
        }
    }

    public String get(String key){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = pool.getResource();
            if (shardedJedis !=null){
                String va = shardedJedis.get(key);
                return va;
            }
        } catch (Exception e){

        }finally {
            if (shardedJedis != null){
                shardedJedis.close();
            }
        }
        return StringUtils.EMPTY;
    }


}
