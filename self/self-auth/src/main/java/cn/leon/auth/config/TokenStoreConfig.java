package cn.leon.auth.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @ClassName TokenStoreConfig
 * @Description
 * @Author Jevon
 * @Date2020/1/15 14:26
 **/
@Configuration
public class TokenStoreConfig {
    /**
     * 存放token
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 用于生成jwt
     *
     * @return
     */
    @Bean
    @Primary
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("self");
        return accessTokenConverter;
    }

    /**
     * 用于扩展JWT
     * @return
     */
    @Bean
    public TokenEnhancer jwtTokenEnhancer(){
        return new SelfTokenEnhancer();
    }
}
