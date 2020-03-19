package com.iafoot.dbandcache.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 单数据源
 *@ MapperScan注解用来指定扫描的 mapper 接口所在的包
 * 读取 配置文件 的 是 org. springframework. core. env. Environment 实例
 *
 *  整个类的流程为： 首先根据数据库配置创建一个DataSource单例，之后根据该DataSource实例和SqlSessionFactoryBean创建 一个SqlSessionFactory 单 例。
 *  之后 SqlSessionFactory 创建 出 SqlSession， 再 使用 SqlSession 获取 相应 的 Mapper 实例， 然后 通过 Mapper 实例 就可以 肆意 地 操作 数据库 了
 *
 *   SqlSessionFactoryBean 中的 TypeAliasesPackage 用来 指定 domain 类 的 基 包， 即 指定 其 在 xxxMapper. xml 文件 中 可以 使用 简 名 来 代替 全 类 名；
 *   MapperLocations 用来 指定 xxxMapper. xml 文件 所在 的 位置， 如果 MyBatis 完全 使用 注解， 则 也可以 不 设置 这 两个 参数。
 *   值得注意 的 是， 这里 使用 的 数据 源 是 阿里巴巴 的 Druid
 */

/**
 * 在该类中**首先创建了两个数据源microservicedb1DataSource和microservicedb2DataSource**，之**后将这两个数据源设置到DynamicDataSource数据源中**。
 * 在**DynamicDataSource中设置了目标数据源map，并且设置了默认的数据源为microservicedb1DataSource，这样以后就不需要为访问microservicedb1DataSource的dao类选择数据源了，直接使用默认的数据源**。
 * 也就是说我们不需要显示为UserDao选择数据源，会默认选择microservicedb1DataSource。而对于下边的CarDao，就需要显示指定其访问的数据源为mircroservicedb2DataSource。
 * 另外，值得注意的是，**MyBatisConfig中的三个数据源都是java.sql.DataSource接口的实现或实现的子类，所以在DynamicDataSource类上添加了@Primary注解，
 * 该注解的作用是“指定在同一个接口有多个实现类可以注入的时候，默认选择哪一个**，而不是让Spring因为有多个注入选择而不知道该选哪个最终导致报错”。
 */
@Configuration
@MapperScan(basePackages = "com.iafoot.dbandcache.mapper")
public class MyBatisConfig {
    @Autowired
    private Environment env;

    @Bean
    public DataSource microservicedb1DataSource() throws Exception{
        Properties props = new Properties();
        props.put("driverClassName",env.getProperty("microservicedb1.jdbc.driverClassName"));
        props.put("url",env.getProperty("microservicedb1.jdbc.url"));
        props.put("username",env.getProperty("microservicedb1.jdbc.username"));
        props.put("password",env.getProperty("microservicedb1.jdbc.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public DataSource microservicedb2DataSource() throws Exception{
        Properties props = new Properties();
        props.put("driverClassName",env.getProperty("microservicedb2.jdbc.driverClassName"));
        props.put("url",env.getProperty("microservicedb2.jdbc.url"));
        props.put("username",env.getProperty("microservicedb2.jdbc.username"));
        props.put("password",env.getProperty("microservicedb2.jdbc.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("microservicedb1DataSource") DataSource microservicedb1DataSource,
                                        @Qualifier("microservicedb2DataSource") DataSource microservicedb2DataSource){
        Map<Object,Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.microservicedb1,microservicedb1DataSource);
        targetDataSources.put(DatabaseType.microservicedb2,microservicedb2DataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);//该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(microservicedb1DataSource);//默认的datasource设置为microservicedb1DataSource
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);//指定数据源
        fb.setTypeAliasesPackage("com.iafoot.dbandcache.model");//指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/*.xml"));//指定xml文件位置
        return fb.getObject();
    }





  /*
  //单数据源配置
  @Bean
    public DataSource dataSource() throws Exception{
        Properties props = new Properties();
        props.put("driverClassName",env.getProperty("microservicedb1.jdbc.driverClassName"));
        props.put("url",env.getProperty("microservicedb1.jdbc.url"));
        props.put("username",env.getProperty("microservicedb1.jdbc.username"));
        props.put("password",env.getProperty("microservicedb1.jdbc.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }
    //单数据源配置
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);
        fb.setTypeAliasesPackage("com.iafoot.dbandcache.model");//指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/*.xml"));//指定xml文件位置
        return fb.getObject();
    }*/













}
