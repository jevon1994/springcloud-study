package cn.leon.kubernetes.serviceregistry;

import cn.leon.kubernetes.feign.KubernetesRegistryClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

@Order(1)
@Aspect
public class RegisterAspect {

    @Pointcut("execution(* org.springframework.cloud.client.serviceregistry.ServiceRegistry.register(..))")
    public void register() {
    }

    @Autowired
    private KubernetesRegistryClient kubernetesRegistryClient;

    @Around("register()")
    public Object beforeRegistry(ProceedingJoinPoint point) {
        Object object = null;
        try {
            System.out.println("register...... start");
            System.out.println(kubernetesRegistryClient.register("register"));
            object = point.proceed();
            System.out.println("register...... done");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;
    }
}
