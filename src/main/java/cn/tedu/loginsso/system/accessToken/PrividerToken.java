package cn.tedu.loginsso.system.accessToken;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 在请求gitee授权之前,需要传递的信息
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
@Component
public class PrividerToken{
    private Integer id; // id
    private String clientId;// 客户端id
    private String redirectUrl;// 回调地址
    private String code;// code
    private String state;// 状态
    private String clientSecret;// 客户端秘钥
}
