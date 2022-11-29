package cn.tedu.loginsso.system.mapper;

import cn.tedu.loginsso.system.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 本地用户的持久层接口
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
