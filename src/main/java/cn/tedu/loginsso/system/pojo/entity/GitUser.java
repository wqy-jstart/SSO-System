package cn.tedu.loginsso.system.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * git用户信息对应的实体类
 */
@Data
public class GitUser implements Serializable {
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

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;
}
