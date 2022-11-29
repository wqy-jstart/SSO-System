package cn.tedu.loginsso.system.web;

import cn.tedu.loginsso.system.ex.ServiceException;
import lombok.Data;

import java.io.Serializable;

/**
 * 该类定义统一返回请求结果的方法,JSON对象类型(状态值+结果信息+返回数据)
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Data
public class JsonResult<T> implements Serializable {

    // 状态码
    private Integer state;

    // 描述文本
    private String message;

    // 操作成功所返回的数据
    private T data;

    /**
     * 成功的静态方法
     * @return 返回state状态为1的JsonResult的对象
     */
    public static JsonResult<Void> ok(){
        return ok(null);
    }

    /**
     * 成功时需要返回数据的ok()方法
     * @param data 传入需要返回的List集合
     * @return 返回包含List集合的JsonResult对象
     */
    public static <T> JsonResult<T> ok(T data){
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.state = ServiceCode.OK.getValue();// 固定20000
        jsonResult.data = data;// 接收传入的数据List集合
        return jsonResult;
    }

    public static JsonResult<Void> fail(ServiceException e){
        return fail(e.getServiceCode(),e.getMessage());
    }

    public static JsonResult<Void> fail(ServiceCode serviceCode,String message){
        JsonResult<Void> jsonResult = new JsonResult<>();
        jsonResult.state = serviceCode.getValue();
        jsonResult.message = message;
        return jsonResult;
    }
}
