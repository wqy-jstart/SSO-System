package cn.tedu.loginsso.system.service.impl;

import cn.tedu.loginsso.system.ex.ServiceException;
import cn.tedu.loginsso.system.mapper.UserMapper;
import cn.tedu.loginsso.system.pojo.DTO.LoginUserDTO;
import cn.tedu.loginsso.system.pojo.DTO.SignUserDTO;
import cn.tedu.loginsso.system.pojo.entity.User;
import cn.tedu.loginsso.system.service.IUserService;
import cn.tedu.loginsso.system.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 本地用户的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 处理用户注册的业务
     * @param signUserDTO 注册的用户数据
     */
    @Override
    public void insert(SignUserDTO signUserDTO) {
        log.debug("开始处理本地用户注册功能,参数:{}",signUserDTO);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",signUserDTO.getUsername());
        Integer count = userMapper.selectCount(wrapper);
        if (count!=0){
            String message = "注册失败,该用户名已经存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }
        log.debug("即将向数据库中添加此用户...");
        User user = new User();
        BeanUtils.copyProperties(signUserDTO,user);
        userMapper.insert(user);
    }

    /**
     * 处理用户登录的业务
     * @param loginUserDTO 注册的用户数据
     */
    @Override
    public void login(LoginUserDTO loginUserDTO) {
        log.debug("开始处理本地用户登录功能,参数:{}",loginUserDTO);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",loginUserDTO.getUsername());
        User user = userMapper.selectOne(wrapper);
        if (user==null){
            String message = "注册失败,该用户名不存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,message);
        }

        log.debug("开始验证密码是否正确...");
        if (!user.getPassword().equals(loginUserDTO.getPassword())){
            String message = "登录失败,密码错误!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_NOT_PASSWORD,message);
        }
    }
}
