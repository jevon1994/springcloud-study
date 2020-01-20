package cn.leon.auth.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @ClassName SelfUser
 * @Description
 * @Author Jevon
 * @Date2020/1/20 16:11
 **/

public class SelfUser extends User {

    public SelfUser(Integer id, Integer groupId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.groupId = groupId;
        this.id = id;
    }

    @Getter
    private Integer id;
    @Getter
    private Integer groupId;
}
