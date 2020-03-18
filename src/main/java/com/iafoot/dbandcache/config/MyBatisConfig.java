package com.iafoot.dbandcache.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
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
@Configuration
@MapperScan(basePackages = "com.iafoot.dbandcache.mapper")
public class MyBatisConfig {
    @Autowired
    private Environment env;
    @Bean
    public DataSource dataSource() throws Exception{
        Properties props = new Properties();
        props.put("driverClassName",env.getProperty("microservicedb1.jdbc.driverClassName"));
        props.put("url",env.getProperty("microservicedb1.jdbc.url"));
        props.put("username",env.getProperty("microservicedb1.jdbc.username"));
        props.put("password",env.getProperty("microservicedb1.jdbc.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);
        fb.setTypeAliasesPackage("com.iafoot.dbandcache.model");//指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/*.xml"));//指定xml文件位置
        return fb.getObject();
    }













}
