路行道远，看的越深懂得越少。

1.版本说明
dbandcache 1.0.0：SpringBoot添加mybatis单数据源。
dbandcache 1.0.1：SpringBoot添加mybatis多数据源。

2.项目背景
是以该项目梳理对SpringBoot、微服务相关知识。

3.相关技术
SPringBoot、Maven、Swagger、阿里巴巴数据源druid

4. 辅助说明
根路径：mybatis-generator，生成数据访问层模板代码。
5.总结




版本关键点详细说明：

	1. DatabaseType列出所有的数据源key作为第5步中所说的key。
	2. DatabaseContextHolder是一个线程安全的DatabaseType容器，并提供了向其中设置和获取DatabaseType的方法。
	3. DynamicDataSource继承AbstractRoutingDataSource并重写其中的方法determineCurrentLookupKey(),在该方法中使用DatabaseContextHolder获取当前线程的DatabaseType。
	4. 在MyBatisCOnfig中生成两个数据源DataSource的bean作为第5步中所说的value。
	5. 在MyBatisConfig中将第1步中的key和第4步中的value组成的key-value对写入DynamicDataSource动态数据源的targetDataSources属性中（当然，同时也会设置两个数据源其中的一个到DynamicDataSource的defalutTargetDataSource属性中）。
	6. 将DynamicDataSource作为primary数据源注入SqlSessionFactory的dataSource属性中。
	7. 使用的时候，在dao层或service层先使用DatabaseContextHolder设置将要使用的数据源key(当然也可以使用Spring AOP去做)，然后再调用mapper层进行相应的操作。在mapper层进行操作的时候，会先调用determineCurrentLookupKey()方法获取一个数据源（获取数据源的方法：先根据设置去targetDataSources中找，若没有，则选择defaultTargetDataSource），之后再进行数据库操作。，**然后再调用mapper层进行相应的操作。在mapper层进行操作的时候，会先调用determineCurrentLookupKey()方法获取一个数据源（获取数据源的方法：先根据设置去targetDataSources中找，若没有，则选择defaultTargetDataSource），之后再进行数据库操作。