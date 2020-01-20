package cn.leon.auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SelfUserDetailService
 * @Description
 * @Author Jevon
 * @Date2020/1/19 17:33
 **/
@Service
public class SelfUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    // todo 持久化到数据库
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> read = new ArrayList() {{
            add(new SimpleGrantedAuthority("read"));
        }};
        String encode = passwordEncoder.encode("123456");
        SelfUser selfUser = new SelfUser(1, 1, username, encode, true, true, true, true, read);
        return selfUser;
    }
}
