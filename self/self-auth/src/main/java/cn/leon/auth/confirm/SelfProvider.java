package cn.leon.auth.confirm;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Provider;

public class SelfProvider extends Provider {

    /**
     * Constructs a provider with the specified name, version number,
     * and information.
     *
     * @param name    the provider name.
     * @param version the provider version number.
     * @param info    a description of the provider and its services.
     */
    protected SelfProvider(String name, double version, String info) {
        super(name, version, info);
    }

    /**
     * 自定义验证
     * @param userDetails
     * @param authentication
     */
    protected void additionalAuthenticationChecks(UserDetails userDetails, SelfProvider authentication) {

    }

    protected final UserDetails retrieveUser(String username, SelfAuthenticationToken authentication)  {
        UserDetails loadedUser;
        try {
            // 调用loadUserByUsername时加入type前缀
//            loadedUser = this.getUserDetailsService().loadUserByUsername(authentication.getType() + ":" + username);
        } catch (UsernameNotFoundException var6) {
            if(authentication.getCredentials() != null) {
                String presentedPassword = authentication.getCredentials().toString();
//                this.passwordEncoder.isPasswordValid(this.userNotFoundEncodedPassword, presentedPassword, (Object)null);
            }

            throw var6;
        } catch (Exception var7) {
            throw new InternalAuthenticationServiceException(var7.getMessage(), var7);
        }

//        if(loadedUser == null) {
//            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
//        } else {
//            return loadedUser;
//        }
        return null;
    }
}
