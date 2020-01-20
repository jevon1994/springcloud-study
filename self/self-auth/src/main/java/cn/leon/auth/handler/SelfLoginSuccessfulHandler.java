package cn.leon.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName SlefLoginSuccessfulHandler
 * @Description
 * @Author Jevon
 * @Date2020/1/19 17:37
 **/
@Slf4j
@Configuration
public class SelfLoginSuccessfulHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功", authentication.getPrincipal());
        response.setContentType("application/json;charset=utf-8");
        response.sendRedirect("/success");
    }
}
