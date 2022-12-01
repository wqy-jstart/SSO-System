package cn.tedu.loginsso.system.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 这是继承了Spring Security框架中User的一个扩展详情类
 * 是Spring Security框架的loadUserByUsername()的返回结果
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class AdminDetails extends User {
    private Long id;

    public AdminDetails(Long id ,
                        String username,
                        String password,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true,
                true, authorities);
        this.id = id;
    }
}
