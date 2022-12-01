package cn.tedu.loginsso.system.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LoginUserDTO implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户名
     */
    private String password;



}
