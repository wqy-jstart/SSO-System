package cn.tedu.loginsso.system.controller;

import cn.tedu.loginsso.system.pojo.DTO.LoginUserDTO;
import cn.tedu.loginsso.system.pojo.DTO.SignUserDTO;
import cn.tedu.loginsso.system.service.IUserService;
import cn.tedu.loginsso.system.web.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本地用户的控制器类
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Validated
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private IUserService userService;

    // 本地用户注册的请求
    @PostMapping("/insert")
    public JsonResult<Void> insert(SignUserDTO signUserDTO){
        log.debug("开始处理用户注册的功能!");
        userService.insert(signUserDTO);
        return JsonResult.ok();
    }

    // 开始处理本地用户登录的请求
    @PostMapping("/login")
    public JsonResult<Void> login(LoginUserDTO loginUserDTO){
        log.debug("开始处理用户登录的功能!");
        userService.login(loginUserDTO);
        return JsonResult.ok();
    }
}
