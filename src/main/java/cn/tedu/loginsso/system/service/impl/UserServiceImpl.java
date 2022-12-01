package cn.tedu.loginsso.system.service.impl;

import cn.tedu.loginsso.system.ex.ServiceException;
import cn.tedu.loginsso.system.mapper.UserMapper;
import cn.tedu.loginsso.system.pojo.DTO.LoginUserDTO;
import cn.tedu.loginsso.system.pojo.DTO.SignUserDTO;
import cn.tedu.loginsso.system.pojo.entity.User;
import cn.tedu.loginsso.system.security.AdminDetails;
import cn.tedu.loginsso.system.service.IUserService;
import cn.tedu.loginsso.system.util.BCryptEncode;
import cn.tedu.loginsso.system.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * 本地用户的业务层接口实现类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    // 读取配置文件application-dev.yml的自定义配置
    @Value("${loginsso.system.jwt.secret-key}")
    private String secretKey;
    @Value("${loginsso.system.jwt.duration-in-minute}")
    private long durationInMinute;

    public UserServiceImpl() {
        log.debug("创建业务层实现类对象:UserServiceImpl");
    }

    // 注入用户的DAO接口
    @Autowired
    private UserMapper userMapper;

    // 注入认证信息接口对象
    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * 处理用户注册的业务
     *
     * @param signUserDTO 注册的用户数据
     */
    @Override
    public void insert(SignUserDTO signUserDTO) {
        log.debug("开始处理本地用户注册功能,参数:{}", signUserDTO);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", signUserDTO.getUsername());
        Integer count = userMapper.selectCount(wrapper);
        if (count != 0) {
            String message = "注册失败,该用户名已经存在!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }
        log.debug("即将向数据库中添加此用户...");
        User user = new User();
        BeanUtils.copyProperties(signUserDTO, user);
        user.setPassword(BCryptEncode.encryptionPassword(signUserDTO.getPassword()));// 对密码进行BCrypt加密处理
        int rows = userMapper.insert(user);
        if (rows > 1) {
            String message = "绑定失败,服务器忙,请稍后再试!";
            log.debug(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }
    }

    /**
     * 处理用户登录的业务
     *
     * @param loginUserDTO 注册的用户数据
     */
    @Override
    public String login(LoginUserDTO loginUserDTO) {
        log.debug("开始处理本地用户登录功能,参数:{}", loginUserDTO);

        // 1.处理认证
        // 创建一个认证器,实例化UsernamePasswordAuthenticationToken类,传入需要认证的用户信息
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(
                loginUserDTO.getUsername(), loginUserDTO.getPassword()
        );

        // 开始认证
        Authentication authenticationResult
                = authenticationManager.authenticate(authentication);

        log.debug("认证通过,认证管理器返回:{}", authenticationResult);

        // 2.认证成功后,从认证结果中获取当事人对象,将用于生成JWT
        Object principal = authenticationResult.getPrincipal();
        log.debug("认证结果中的当事人类型{}", principal.getClass().getName());
        AdminDetails adminDetails = (AdminDetails) principal;

        // 3.获取认证结果
        String username = adminDetails.getUsername();
        Long id = adminDetails.getId();
        Collection<GrantedAuthority> authorities = adminDetails.getAuthorities();// 获取认证结果的权限信息,Granted Authorities=[?,?...]
        String authoritiesJsonString = JSON.toJSONString(authorities);

        // 生成JWT数据前将用户的数据进行填充
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("id", id);
        claims.put("authoritiesJsonString", authoritiesJsonString);
        log.debug("向JWT中存入用户名:{}", username);
        log.debug("向JWT中存入id:{}", id);
        log.debug("向JWT中存入authoritiesJsonString:{}", authoritiesJsonString);

        // 4.生成JWT数据
        Date date = new Date(System.currentTimeMillis() + durationInMinute * 60 * 1000L);
        String jwt = Jwts.builder() // 构建者模式
                // header
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("trp", "JWT")
                // Payload 载荷
                .setClaims(claims)
                // Signature 签名
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        log.debug("生成JWT:{}", jwt);
        return jwt;
    }

    // 主页显示本地用户的信息
    @Override
    public User selectByUsername(String username) {
        log.debug("开始根据用户名[{}]查询本地用户信息...", username);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }
}
