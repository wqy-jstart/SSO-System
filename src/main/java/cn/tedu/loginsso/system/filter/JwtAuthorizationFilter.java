package cn.tedu.loginsso.system.filter;

import cn.tedu.loginsso.system.security.LoginPrincipal;
import cn.tedu.loginsso.system.web.JsonResult;
import cn.tedu.loginsso.system.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * JWT过滤器---该过滤器仅对客户端登录后发出请求时携带的JWT做检查,后续不管
 */
@Slf4j
@Component// 声明是一个组件
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${loginsso.system.jwt.secret-key}")
    private String secretKey;

    public static final int JWT_MIN_LENGTH = 113;

    public JwtAuthorizationFilter() {
        log.debug("创建过滤器对象:JwtAuthorizationFilter");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("JwtAuthorizationFilter开始过滤...");

        // 清空Security上下文
        // Security上下文中的认证信息也会被清除
        // 避免前序携带JWT且解析成功后将认证信息存入Security上下文后,后续不携带JWT也能访问的"问题"
        SecurityContextHolder.clearContext();

        // 获取请求头'Authorization'中的JWT内容
        String jwt = request.getHeader("Authorization");
        log.debug("获取客户携带的JWT为{}", jwt);

        // 检查是否获取到了基本有效的JWT
        if (!StringUtils.hasText(jwt) || jwt.length() < JWT_MIN_LENGTH) {
            // 对于无效的JWT,直接放行,交由后续的组件进行处理
            log.debug("获取到的JWT被视为无效,当前过滤器将放行...");
            filterChain.doFilter(request, response);
            return;
        }

        // 尝试解析JWT(在解析时应当手动捕获异常,否则无法通过过滤器,业务也不会执行)
        log.debug("获取到的JWT被视为有效,准备解析JWT...");
        response.setContentType("application/json;charset=utf-8");
        Claims claims;
        try {
            claims = Jwts.parser() // 调用解析器,传入签名和jwt
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (SignatureException e) {
            log.debug("解析JWT时出现SignatureException---JWT签名不匹配!");
            String message = "非法访问!";
            // new 一个JsonResult用来向客户端返回Json数据
            JsonResult<Void> jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_SIGNATURE, message);
            // 引入FastJson框架依赖
            String jsonResultString = JSON.toJSONString(jsonResult);
            PrintWriter writer = response.getWriter();
            writer.println(jsonResultString);
            return;
        } catch (ExpiredJwtException e) {
            log.debug("解析JWT时出现ExpiredJwtException---JWT已过期!");
            String message = "登录信息已过期，请重新登录！";
            JsonResult<Void> jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_EXPIRED, message);
            String jsonResultString = JSON.toJSONString(jsonResult);
            PrintWriter writer = response.getWriter();// 利用response获取输出字符流
            writer.println(jsonResultString);// 向浏览器输出message
            return;
        } catch (Throwable e) {
            log.debug("解析JWT时出现Throwable，需要开发人员在JWT过滤器补充对异常的处理");
            e.printStackTrace();// 打印相关异常信息
            String message = "你有异常没有处理，请根据服务器端控制台的信息，补充对此类异常的处理！！！";
            PrintWriter writer = response.getWriter();// 利用response获取输出字符流
            writer.println(message);// 向浏览器输出message
            return;
        }

        // 获取解析JWT后的用户名和id
        String username = claims.get("username", String.class);
        Long id = claims.get("id", Long.class);
        String authoritiesJsonString = claims.get("authoritiesJsonString", String.class);
        log.debug("从JWT中取出用户名:{}", username);
        log.debug("从JWT中取出id:{}", id);
        log.debug("从JWT中取出authoritiesJsonString:{}", authoritiesJsonString);

        // 处理权限信息---将取出的权限字符串反序列化为Collection<? extends GrantedAuthority>格式的对象(List是其子类)
        List<SimpleGrantedAuthority> authorities
                = JSON.parseArray(authoritiesJsonString, SimpleGrantedAuthority.class);

        // 创建一个登录时的当事人对象,传入解析后的用户名和id
        LoginPrincipal loginPrincipal = new LoginPrincipal(username, id);
        // 创建一个UsernamePasswordAuthenticationToken,传入用户名和权限信息,返回Authentication认证器对象
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginPrincipal, null, authorities
        );

        // 将Authentication对象的引用存入到SecurityContext上下文中(Spring规定)
        log.debug("向SecurityContext中存入认证信息:{}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);// 传入认证信息到Security Context上下文中

        // 过滤器链继续向后传递,即:放行
        filterChain.doFilter(request, response);
    }
}
