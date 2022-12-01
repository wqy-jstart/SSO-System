package cn.tedu.loginsso.system.security;

import cn.tedu.loginsso.system.mapper.UserMapper;
import cn.tedu.loginsso.system.pojo.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于Spring Security的登录认证创建的用户登录的(UserDetailsService接口)实现类
 * 最终返回UserDetails,以便进行认证
 *
 * @Author java.@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.debug("Spring Security框架自动调用UserDetailServiceImpl中的loadUserByUsername方法,参数{}", s);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",s);
        User user = userMapper.selectOne(wrapper);
        log.debug("从数据库中根据用户名[{}]查询用户信息,结果:{}", s, user);

        if (user == null){
            log.debug("没有与用户名[{}]匹配的用户信息,即将抛出BadCredentialsException", s);
            String message = "登录失败,用户名不存在!";
            throw new BadCredentialsException(message);// Spring Security提供的异常
        }

        // 添加权限信息
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("这是一条假的权限信息");
        authorities.add(authority);

        // 使用新建的继承Spring Security的类AdminDetail来返回信息便于认证
        AdminDetails userDetails = new AdminDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities
        );
        log.debug("即将向Spring Security框架返回UserDetail对象:{}", userDetails);
        return userDetails; // 最终返回登录认证所需要的信息
    }
}
