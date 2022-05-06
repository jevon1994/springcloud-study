package cn.leon.kubernetes.bootstrap;

import cn.leon.kubernetes.config.SelfLocator;
import cn.leon.kubernetes.feign.CloudNativeConfigDecoder;
import cn.leon.kubernetes.feign.ConfigServiceClient;
import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class CloudNativeBootstrapConfiguration {

    @Bean
    @ConditionalOnClass(ConfigServiceClient.class)
    public SelfLocator selfLocator(ConfigClientProperties defaultProperties, ConfigServiceClient configServiceClient) {
        return new SelfLocator(defaultProperties, configServiceClient);
    }

    @Bean
    public ConfigServiceClient configServiceClient() {
        return Feign.builder()
                .decoder(new CloudNativeConfigDecoder())
                .contract(new SpringMvcContract())
                .target(ConfigServiceClient.class, "http://localhost:9000");
    }
}
