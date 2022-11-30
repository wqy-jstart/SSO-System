package cn.tedu.loginsso.system.ex.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 这是一个处理类
 * 填充数据中两个特殊字段的值gmt_create,gmt_modified
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("====start insert ====");

        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);

    }

    // 更新时的策略
    @Override
    public void updateFill(MetaObject metaObject) {

        log.info("====start update====");
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
