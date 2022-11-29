package cn.tedu.loginsso.system.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus的配置类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Configuration
@MapperScan("cn.tedu.loginsso.system.mapper")
public class MyBatisPlusConfiguration {

    // 测试配置类是否配置成功!
    public MyBatisPlusConfiguration(){
        log.debug("创建配置类对象:MyBatisPlusConfiguration");
    }
}
