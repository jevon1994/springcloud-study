package cn.leon.kubernetes.autoconfig;

import cn.leon.kubernetes.condition.AllRegistryCondition;
import cn.leon.kubernetes.condition.CloudNativeCondition;
import cn.leon.kubernetes.feign.FeignClientsServiceNameAppendBeanPostProcessor;
import cn.leon.kubernetes.feign.KubernetesRegistryClient;
import cn.leon.kubernetes.model.CloudNativeProperties;
import cn.leon.kubernetes.serviceregistry.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

/**
 * @author mujian
 * @Classname CloudNativeAutoConfiguration
 * @Description
 * @Date 2021/12/27
 */
@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {KubernetesRegistryClient.class})
public class CloudNativeAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "cloud-native.gateway", name = "routing", havingValue = "true", matchIfMissing = true)
    public BeanPostProcessor beanPostProcessor(){
        return new FeignClientsServiceNameAppendBeanPostProcessor();
    }

    @Bean
    @ConditionalOnProperty(prefix = "cloud-native.discovery", name = "type", havingValue = "all", matchIfMissing = true)
    public RegisterAspect registerAspect() {
        return new RegisterAspect();
    }

    @Bean
    @Conditional(AllRegistryCondition.class)
    public CloudNativeRegisterService cloudNativeRegisterService(KubernetesRegistryClient kubernetesRegistryClient, Environment environment, CloudNativeProperties cloudNativeProperties) {
        return new CloudNativeRegisterService(kubernetesRegistryClient, environment, cloudNativeProperties);
    }

    @Configuration(proxyBeanMethods = false)
    @Conditional(CloudNativeCondition.class)
    public static class CloudNativeRegisterAutoConfiguration {

        @Bean
        @Primary
        public CloudNativeServiceRegistry cloudNativeServiceRegistry(CloudNativeRegisterService cloudNativeRegisterService) {
            return new CloudNativeServiceRegistry(cloudNativeRegisterService);
        }

        @Bean
        @Primary
        public CloudNativeRegistration cloudNativeRegistration() {
            return new CloudNativeRegistration();
        }

        @Bean
        public AutoServiceRegistration autoServiceRegistration(ApplicationContext context, CloudNativeServiceRegistry registry, CloudNativeRegistration registration) {
            return new CloudNativeAutoServiceRegistration(context, registry, registration);
        }
    }
}
