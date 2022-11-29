package cn.tedu.loginsso.system.pojo.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册的实体类
 */
@Data
public class SignUserDTO implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户名
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;
}
