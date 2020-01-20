package cn.leon.auth.config;

import cn.leon.auth.handler.SelfLoginFailureHandler;
import cn.leon.auth.handler.SelfLoginSuccessfulHandler;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName WebSecurityConfig
 * @Description
 * @Author Jevon
 * @Date2020/1/15 16:27
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SelfLoginSuccessfulHandler selfLoginSuccessfulHandler;
    @Autowired
    private SelfLoginFailureHandler selfLoginFailureHandler;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(selfLoginSuccessfulHandler)
                .failureHandler(selfLoginFailureHandler)
                .and().authorizeRequests()
                .antMatchers(
                        "/authentication/require",
                        "/authentication/form",
                        "/**/*.js",
                        "/**/*.css",
                        "/**/*.jpg",
                        "/**/*.png",
                        "/**/*.woff2"
                )
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
//        http.formLogin()
//                .loginPage("/authentication/require")
//                .loginProcessingUrl("/authentication/form")
//                .successHandler(getSuccessHandler())
//                .and()
//                .authorizeRequests()
//                .antMatchers(
//                        "/authentication/require",
//                        "/authentication/form",
//                        "/**/*.js",
//                        "/**/*.css",
//                        "/**/*.jpg",
//                        "/**/*.png",
//                        "/**/*.woff2",
//                        "/code/*")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and().httpBasic().disable().csrf().disable().cors();

//        http.headers().frameOptions().disable().and()
//                .formLogin()//使用表单登录，不再使用默认httpBasic方式
//                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)//如果请求的URL需要认证则跳转的URL
//                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)//处理表单中自定义的登录URL
//                .successHandler(merryyouLoginSuccessHandler)//登录成功处理器，返回JSON
//                .failureHandler(merryyouAuthenticationfailureHandler)//登录失败处理器
//                .and()
//                .apply(validateCodeSecurityConfig)//验证码拦截
//                .and()
//                .apply(smsCodeAuthenticationSecurityConfig)
//                .and()
//                .apply(merryyouSpringSocialConfigurer)//社交登录
//                .and()
//                .rememberMe()
//                .tokenRepository(persistentTokenRepository())
//                .tokenValiditySeconds(securityProperties.getRememberMeSeconds())
//                .userDetailsService(userDetailsService)
//                .and()
//                .sessionManagement()
////                .invalidSessionStrategy(invalidSessionStrategy)
//                .invalidSessionUrl("/session/invalid")
//                .maximumSessions(securityProperties.getSession().getMaximumSessions())//最大session并发数量1
//                .maxSessionsPreventsLogin(securityProperties.getSession().isMaxSessionsPreventsLogin())//之后的登录踢掉之前的登录
//                .expiredSessionStrategy(sessionInformationExpiredStrategy)
//                .and()
//                .and()
//                .logout()
//                .logoutUrl("/signOut")//默认退出地址/logout
//                .logoutSuccessUrl("/")//退出之后跳转到注册页面
//                .deleteCookies("JSESSIONID")
//                .and()
//                .authorizeRequests().antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
//                SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM,
//                SecurityConstants.DEFAULT_REGISTER_URL,
//                SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
//                SecurityConstants.DEFAULT_SIGN_IN_URL_MOBILE_PAGE,
//                "/register",
//                "/socialRegister",//社交账号注册和绑定页面
//                "/user/register",//处理社交注册请求
//                "/social/info",//获取当前社交用户信息
//                "/session/invalid",
//                "/**/*.js",
//                "/**/*.css",
//                "/**/*.jpg",
//                "/**/*.png",
//                "/**/*.woff2",
//                "/code/*")
//                .permitAll()//以上的请求都不需要认证
//                //.antMatchers("/").access("hasRole('USER')")
//                .and()
//                .csrf().disable()//关闭csrd拦截
//        ;
//        //安全模块单独配置
//        authorizeConfigProvider.config(http.authorizeRequests());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }
}
