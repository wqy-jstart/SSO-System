package cn.tedu.loginsso.system.service.impl;

import cn.tedu.loginsso.system.ex.ServiceException;
import cn.tedu.loginsso.system.mapper.GitUserMapper;
import cn.tedu.loginsso.system.pojo.entity.GitUser;
import cn.tedu.loginsso.system.pojo.entity.User;
import cn.tedu.loginsso.system.service.IGitService;
import cn.tedu.loginsso.system.web.ServiceCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * git用户业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class GitUserServiceImpl implements IGitService {

    @Autowired
    private GitUserMapper gitUserMapper;

    @Override
    public void insert(GitUser gitUser) {
        log.debug("处理添加git用户的请求...参数:{}",gitUser);
        GitUser user = gitUserMapper.selectById(gitUser.getId());
        if (user !=null){
            String message = "绑定失败,该gitee用户已存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        int count = gitUserMapper.insert(gitUser);
        if (count>1){
            String message = "绑定失败,服务器忙,请稍后再试!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT,message);
        }

    }

    // 根据登录昵称查询用户
    @Override
    public GitUser selectByLogin(String loginName) {
        log.debug("处理查询git用户的请求...参数:{}",loginName);
        QueryWrapper<GitUser> wrapper = new QueryWrapper<>();
        wrapper.eq("login",loginName);
        return gitUserMapper.selectOne(wrapper);
    }
}
