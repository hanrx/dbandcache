package com.iafoot.dbandcache;

import com.iafoot.dbandcache.config.MyBatisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@SpringBootApplication
@EnableSwagger2
//项目中自己定义了动态数据源配置类DynamicDataSourceConfig，所以要排除Spring的自动配置数据源DataSourceAutoConfiguration，否则会报循环引用的错误
//Spring boot启动的时候排除Spring的，并倒入自己的动态数据源类
@Import({MyBatisConfig.class})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DbandcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbandcacheApplication.class, args);
    }

}
