#路行道远，看的越深懂得越少。

## 1.版本说明
* dbandcache 1.0.0：SpringBoot添加mybatis单数据源。
* dbandcache 1.0.1：SpringBoot添加mybatis多数据源。
* dbandcache 1.0.2：SpringBoot集成ShardJedis Redis缓存支持。

## 2.项目背景
* 是以该项目梳理对SpringBoot、微服务相关知识。

## 3.相关技术
* SpringBoot、Maven、Swagger、阿里巴巴数据源druid、Jedis、commons-lang3、fastjson。

## 4. 辅助说明
* 根路径：mybatis-generator，生成数据访问层模板代码。

# 5.总结




# 版本关键点详细说明：

## dbandcache 1.0.1：SpringBoot添加mybatis多数据源。
* 1.DatabaseType列出所有的数据源key作为第5步中所说的key。
* 2.DatabaseContextHolder是一个线程安全的DatabaseType容器，并提供了向其中设置和获取DatabaseType的方法。
* 3.DynamicDataSource继承AbstractRoutingDataSource并重写其中的方法determineCurrentLookupKey(),在该方法中使用DatabaseContextHolder获取当前线程的DatabaseType。
* 4.在MyBatisCOnfig中生成两个数据源DataSource的bean作为第5步中所说的value。
* 5.在MyBatisConfig中将第1步中的key和第4步中的value组成的key-value对写入DynamicDataSource动态数据源的targetDataSources属性中（当然，同时也会设置两个数据源其中的一个到DynamicDataSource的defalutTargetDataSource属性中）。
* 6.将DynamicDataSource作为primary数据源注入SqlSessionFactory的dataSource属性中。
* 7.使用的时候，在dao层或service层先使用DatabaseContextHolder设置将要使用的数据源key(当然也可以使用Spring AOP去做)，然后再调用mapper层进行相应的操作。在mapper层进行操作的时候，会先调用determineCurrentLookupKey()方法获取一个数据源（获取数据源的方法：先根据设置去targetDataSources中找，若没有，则选择defaultTargetDataSource），之后再进行数据库操作。，**然后再调用mapper层进行相应的操作。在mapper层进行操作的时候，会先调用determineCurrentLookupKey()方法获取一个数据源（获取数据源的方法：先根据设置去targetDataSources中找，若没有，则选择defaultTargetDataSource），之后再进行数据库操作。


引入了Jedis、在Spring Boot 1.4.3版本下，Jedis的版本默认是2.8.2，所以我们不需要指定版本。需要注意的是，太低版本的Jedis的jar包有一些bug，所以尽量使用2.8.0+的版本。引入commons-lang3，该包下有一系列的工具类，例如StringUtils、NumberUtils等，方便我们开发。引入阿里的fastjson，该JSON工具类相较于Jackson使用起来更方便顺手。

## dbandcache 1.0.2：SpringBoot集成ShardJedis Redis缓存支持。

* Spring Boot集成ShardJedis
    * 依赖：
        * Jedis。
    * 引入commons-lang3：有一系列的工具类 方便开发。
    * 引入fastjson：该JSON工具类较Jackson方便顺手。       
    * 配置Redis信息：
        * Redis server：IP 、端口。
        * maxTotal：同时建立最大连接个数（最多可分配ShardJedis实例数），默认8个，设置为-1，表示不限制。
    * 集成分片版的Jedis：
        * ShardedJedisPool：每台服务器封装成一个JedisShardInfo，通过这些JedisShardInfo组成的服务器列表以及Redis的配置信息JedisPoolConfig创建了一个ShardedJedisPool。
        * maxTotal：指定同时获取多少个ShardJedis连接实例。
    * 创建RedisUtil：操作缓存的工具类。
        * 创建相应方法：set和get。set和get的逻辑：
            * 获取操作Redis的连接：首先从ShardedJedisPool中获取shardedJedis，可理解为一个连接。
            * 数据库的操作：之后使用该连接进行数据库的操作。
            * 资源关闭：将shardedJedis资源关闭。
    * 使用：编写一个缓存前缀指定类：
        * 目的：防止缓存key冲突和增强语义。













