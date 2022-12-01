package cn.tedu.loginsso.system.config;

import cn.tedu.loginsso.system.filter.JwtAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 创建Spring Security的配置类
 * 配置放行路径,登录页面,认证授权,禁用"防止伪造的跨域攻击"
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)// 启用方法级别的权限检查!
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfiguration(){
        log.debug("创建配置类对象:SecurityConfiguration");
    }

    @Bean // 添加Bean注解,该对象会被Spring自动调用
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 重写认证信息接口
     * @return AuthenticationManager
     * @throws Exception Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 重写认证的配置方法
     * @param http
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 白名单路径 这些路径不需要登录即可访问(但可能会有未放行的文件不能显示)---白名单外403
        String[] urls = {
                "/favicon.ico",
                "/doc.html",
                "/**/*.js",
                "/**/*.css",
                "/swagger-resources",
                "/v2/api-docs",
                "/users/login",
                "/users/insert",
        };

        // ★客户端请求时携带了请求头,称为复杂请求OPTIONS,该请求会经过预检机制(PreFlight),解决方案目前有两种
        // 1.该方法会自动启用一个CorsFilter,这是Spring Security内置专门用于处理跨域问题的过滤器,也会对OPTIONS请求放行,效果完全相同!!
        http.cors();

        // 将"防止伪造跨域攻击的机制"禁用(如果不添加该配置,Post请求会403---浏览器的安全措施)
        http.csrf().disable();

        // 添加这些方法,可以手动匹配进行随机认证-----链式写法
        // 提示: 关于请求路径的配置,如果同一路径对应多项配置规则,以第1次配置为准
        http.authorizeRequests() // 管理请求授权

                //2. ↓↓↓↓ 对所有OPTIONS请求直接放行 ↓↓↓↓↓
//                .mvcMatchers(HttpMethod.OPTIONS,"/**")
//                .permitAll()

                .mvcMatchers(urls) // 可匹配的路径
                .permitAll() // 直接许可,即可不需要通过认证即可访问
                .anyRequest() // 除了以上配置过的以外的其他所有请求
                .authenticated(); // 要求是"已经通过认证的"

        // 将JWT过滤器添加到Spring Security框架的过滤器链中
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        // 启用登录 :
        // -- 如果启用了表单,会自动重定向到登录表单
        // -- 如果未启用表单,则会提示403错误
//        http.formLogin();
    }
}
