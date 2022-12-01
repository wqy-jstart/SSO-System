package cn.tedu.loginsso.system.service;

import cn.tedu.loginsso.system.pojo.DTO.LoginUserDTO;
import cn.tedu.loginsso.system.pojo.DTO.SignUserDTO;
import cn.tedu.loginsso.system.pojo.entity.User;
import cn.tedu.loginsso.system.pojo.vo.UserStandardVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 本地用户的业务层接口
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Transactional
public interface IUserService {

    /**
     * 注册用户
     * @param signUserDTO 注册的用户数据
     */
    void insert(SignUserDTO signUserDTO);

    /**
     * 用户登录
     * @param loginUserDTO 用户登录提供的数据
     */
    String login(LoginUserDTO loginUserDTO);

    User selectByUsername(String username);
}
