package cn.tedu.loginsso.system.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 本地用户的实体类
 */
@Data
@AllArgsConstructor
public class User implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户名
     */
    private String password;
}
