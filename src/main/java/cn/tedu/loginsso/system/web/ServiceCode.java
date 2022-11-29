package cn.tedu.loginsso.system.web;

import lombok.Data;

/**
 * 业务状态码
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
public enum ServiceCode {

    // 使用枚举中的常量时相当于调用枚举的构造方法 例:OK()
    /**
     * 成功!
     */
    OK(20000),

    /**
     * 错误: 检查参数产生的错误
     */
    ERR_BAD_REQUEST(40000),

    /**
     * 登录失败,用户名或密码错误
     */
    ERR_UNAUTHORIZED(40100),

    /**
     * 错误: 数据不存在
     */
    ERR_NOT_FOUND(40400),

    /**
     * 错误: 用户名不存在
     */
    ERR_NOT_FOUND_LOGIN(40500),

    /**
     * 错误: 密码错误
     */
    ERR_NOT_PASSWORD(40600),

    /**
    * 错误: 数据冲突
     */
    ERR_CONFLICT(40900),

    /**
     * 错误: 插入数据异常
     */
    ERR_INSERT(50000),

    /**
     * 错误: 删除数据异常
     */
    ERR_DELETE(50100),

    /**
     * 错误: 修改数据异常
     */
    ERR_UPDATE(50200),

    /**
     * 错误: 查询数据异常
     */
    ERR_SELECT(50300);


    private Integer value;

    private ServiceCode(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }

}
