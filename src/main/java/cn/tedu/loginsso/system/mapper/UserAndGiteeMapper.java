package cn.tedu.loginsso.system.mapper;

import cn.tedu.loginsso.system.pojo.entity.UserAndGitee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户和gitee关联表的持久层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Repository
public interface UserAndGiteeMapper extends BaseMapper<UserAndGitee> {
}
