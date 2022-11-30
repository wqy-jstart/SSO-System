package cn.tedu.loginsso.system.controller;

import cn.tedu.loginsso.system.accessToken.PrividerToken;
import cn.tedu.loginsso.system.pojo.vo.GitUserStandardVO;
import cn.tedu.loginsso.system.privider.AccessPrivider;
import cn.tedu.loginsso.system.web.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/git")
public class AccessTokenController {

    @Autowired
    private PrividerToken prividerToken;

    @Autowired
    private AccessPrivider accessPrivider;

    @Autowired
    private GitUserStandardVO gitUserStandardVO;

    /**
     * 根据认证Token返回用户的信息
     * @param code 密码
     * @param state 状态
     * @return 返回用户对象(包含用户的信息)
     */
    @GetMapping("/getToken.html")
    public void getToken(@RequestParam(name = "code") String code,
                                                  @RequestParam(name = "state") String state) {
        log.debug("接收成功!code为:{};state为:{}",code,state);
        prividerToken.setClientId("b17799109ce3107be91362aa3130ac2408e02d1f0725cefbe8abf858a4ba3f8c");
        prividerToken.setCode(code);
        prividerToken.setRedirectUrl("http://localhost:8801/git/getToken.html");
        prividerToken.setState(state);
        prividerToken.setClientSecret("41145fe42772cdb1c51ce1edce55d0fbacbcfc2e194ee83d74cda73647d732c1");
        String token = accessPrivider.getToken(prividerToken);
        log.debug("获取的Token为:{}",token);
        gitUserStandardVO = accessPrivider.getUser(token);
    }

    /**
     * 将用户信息返回至客户端
     * @return user实体
     */
    @GetMapping("/getInfo")
    public JsonResult<GitUserStandardVO> getUser(){
        log.debug("开始处理获取用户信息的请求...数据:{}",gitUserStandardVO);
        return JsonResult.ok(gitUserStandardVO);
    }
}
