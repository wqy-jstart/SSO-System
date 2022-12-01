package cn.tedu.loginsso.system.mapper;

import cn.tedu.loginsso.system.pojo.entity.GitUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * git用户的持久层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Repository
public interface GitUserMapper extends BaseMapper<GitUser> {
}
