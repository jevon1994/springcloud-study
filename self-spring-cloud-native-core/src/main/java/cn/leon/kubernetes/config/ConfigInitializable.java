package cn.leon.kubernetes.config;

import org.springframework.cloud.config.environment.Environment;
import org.springframework.core.env.CompositePropertySource;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mujian
 * @Classname ConfigInitializable
 * @Description
 * @Date 2022/1/26
 */
public interface ConfigInitializable {

    AtomicInteger feignProcessor = new AtomicInteger(0);
    AtomicInteger configProcessor = new AtomicInteger(0);
    AtomicInteger envProcessor = new AtomicInteger(0);

    /**
     * 功能描述: 获取配置
     * @return: org.springframework.cloud.config.environment.Environment
     * @auther: mujian
     * @date: 2022/2/17 10:41
     */
    CompositePropertySource getConfig();

    String getDiscoveryType();
}
