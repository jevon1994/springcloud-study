package cn.leon.kubernetes.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
public class TestBootStrapProperties {

    @Bean
    public CloudNativeConfigProperties cloudNativeConfigProperties() {
        return new CloudNativeConfigProperties();
    }
}
