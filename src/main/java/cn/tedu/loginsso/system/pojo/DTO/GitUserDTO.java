package cn.tedu.loginsso.system.pojo.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 向gitee传递token,并获取需要的用户信息
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
@Component
public class GitUserDTO implements Serializable {
    /**
     * git用户id
     */
    private Integer id;

    /**
     * git账号名称
     */
    private String login;

    /**
     * 用户名
     */
    private String name;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 个性签名
     */
    private String bio;
    /**
     * 头像路径
     */
    private String avatarUrl;

    /**
     * 公开作品量
     */
    private int publicRepos;

    /**
     * 粉丝数量
     */
    private int followers;

    /**
     * 关注数量
     */
    private int following;

    /**
     * 星选集数量
     */
    private int stared;

    /**
     * 浏览数量
     */
    private int watched;
}
