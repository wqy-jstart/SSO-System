package cn.tedu.loginsso.system.privider;

import cn.tedu.loginsso.system.accessToken.PrividerToken;
import cn.tedu.loginsso.system.pojo.DTO.GitUserDTO;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 该方法用来处理访问授权的权限
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Component
public class AccessPrivider {

    /**
     * 该方法用来获取授权后的用户信息
     * @param prividerToken 授权需要的信息对象
     * @return null
     */
    public String getToken(PrividerToken prividerToken) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(prividerToken));
        Request request = new Request.Builder()
                // 请求授权的url
                .url("https://gitee.com/oauth/token?grant_type=authorization_code&code=" + prividerToken.getCode() + "&client_id=" + prividerToken.getClientId() + "&redirect_uri=" + prividerToken.getRedirectUrl() + "&client_secret=" + prividerToken.getClientSecret() + "")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {// 响应token
            String string = response.body().string();
            String str = string.split(":")[1];
            String token = str.split("\"")[1];
            System.out.println("token=" + token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 该方法用来根据获取的token来得到用户信息
     * @param token gitee传递的token
     * @return null
     */
    public GitUserDTO getUser(String token) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token=" + token)// 请求用户信息的url
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GitUserDTO gitUserDTO = JSON.parseObject(string, GitUserDTO.class);
            return gitUserDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
