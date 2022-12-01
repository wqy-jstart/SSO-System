package cn.tedu.loginsso.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户和gitee的关联表
 */
@Data
@TableName(value = "sso_user_git")
public class UserAndGitee implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * gitee的id
     */
    private Long gitId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
