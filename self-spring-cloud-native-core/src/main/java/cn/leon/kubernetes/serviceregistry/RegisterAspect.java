package cn.leon.kubernetes.serviceregistry;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mujian
 * @Classname RegisterAspect
 * @Description
 * @Date 2022/1/26
 */
@Aspect
public class RegisterAspect {

    public static final Logger log = LoggerFactory.getLogger(RegisterAspect.class);

    @Pointcut("execution(* org.springframework.cloud.client.serviceregistry.ServiceRegistry.register(..))")
    public void register() {
    }

    @Autowired
    private CloudNativeRegisterService cloudNativeRegisterService;

    @Before("register()")
    public void beforeRegistry(JoinPoint joinPoint) {
        cloudNativeRegisterService.register();
    }
}

