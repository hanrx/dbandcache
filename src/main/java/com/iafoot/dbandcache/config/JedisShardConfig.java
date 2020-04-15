package com.iafoot.dbandcache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JedisShardConfig {
    @Autowired
    private Environment env;

    @Bean
    public ShardedJedisPool shardedJedisPool(){
        String servers = env.getProperty("redis.shard.servers");
        String[] serverArray = servers.split(",");//获取服务器数组
        int timeout = Integer.valueOf(env.getProperty("redis.shard.timeout"));//超时时间
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(env.getProperty("redis.shard.timeout")));

        List<JedisShardInfo> jedisList = new ArrayList<JedisShardInfo>(1);
        for (String server : serverArray){
            String[] hostAndPort = server.split(":");
            JedisShardInfo shardInfo  = new JedisShardInfo(hostAndPort[0],Integer.valueOf(hostAndPort[1]),timeout);
            jedisList.add(shardInfo);
        }
        return  new ShardedJedisPool(config,jedisList);//构建jedis池
    }
}
