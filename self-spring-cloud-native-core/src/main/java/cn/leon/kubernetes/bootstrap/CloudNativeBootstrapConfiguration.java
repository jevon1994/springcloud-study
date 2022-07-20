package cn.leon.kubernetes.bootstrap;

import cn.leon.kubernetes.config.CloudNativeConfigLocator;
import cn.leon.kubernetes.decoder.CloudNativeConfigDecoder;
import cn.leon.kubernetes.feign.CloudFeignRequestInterceptor;
import cn.leon.kubernetes.feign.ConfigServiceClient;
import cn.leon.kubernetes.model.CloudNativeProperties;
import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

/**
 * @author mujian
 * @Classname CloudNativeBootstrapConfiguration
 * @Description config init
 * @Date 2022/1/26
 */
@Configuration(proxyBeanMethods = false)
@Import({CloudNativeProperties.class})
public class CloudNativeBootstrapConfiguration {

    @Bean
    @Primary
    @ConditionalOnClass(ConfigServiceClient.class)
    public CloudNativeConfigLocator cloudNativeConfigLocator(ConfigClientProperties defaultProperties, ConfigServiceClient configServiceClient, CloudNativeProperties cloudNativeProperties) {
        return new CloudNativeConfigLocator(defaultProperties, configServiceClient, cloudNativeProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "cloud-native.gateway", value = "routing", havingValue = "true", matchIfMissing = true)
    public CloudFeignRequestInterceptor cloudFeignRequestInterceptor(CloudNativeProperties cloudNativeProperties) {
        return new CloudFeignRequestInterceptor(cloudNativeProperties);
    }

    @Bean
    @ConditionalOnClass(CloudFeignRequestInterceptor.class)
    @ConditionalOnProperty(prefix = "cloud-native.gateway", value = "routing", havingValue = "true", matchIfMissing = true)
    public ConfigServiceClient configServiceClientViaRouting(CloudNativeProperties cloudNativeProperties, CloudFeignRequestInterceptor cloudFeignRequestInterceptor) {
        return getConfigServiceClient(cloudNativeProperties, null);
    }

    @Bean
    @ConditionalOnProperty(prefix = "cloud-native.gateway", value = "routing", havingValue = "false")
    public ConfigServiceClient configServiceClient(CloudNativeProperties cloudNativeProperties) {
        return getConfigServiceClient(cloudNativeProperties, null);
    }

    private ConfigServiceClient getConfigServiceClient(CloudNativeProperties cloudNativeProperties, CloudFeignRequestInterceptor cloudFeignRequestInterceptor) {
        Feign.Builder contract = Feign.builder()
                .decoder(new CloudNativeConfigDecoder())
                .contract(new SpringMvcContract());
        if (cloudFeignRequestInterceptor != null) {
            contract.requestInterceptor(cloudFeignRequestInterceptor);
        }
        return contract
                .target(ConfigServiceClient.class, cloudNativeProperties.getConfig().getUrl());
    }
}
