/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.leon.kubernetes.autoconfig;

import cn.leon.kubernetes.feign.CloudFeignRequestInterceptor;
import cn.leon.kubernetes.feign.KubernetesRegistryClient;
import cn.leon.kubernetes.serviceregistry.CloudNativeAutoServiceRegistration;
import cn.leon.kubernetes.serviceregistry.CloudNativeRegistration;
import cn.leon.kubernetes.serviceregistry.CloudNativeServiceRegistry;
import cn.leon.kubernetes.serviceregistry.RegisterAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class CloudNativeAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "cloud-native.discovery", name = "type", havingValue = "all")
    public RegisterAspect registerAspect() {
        return new RegisterAspect();
    }

    @Bean
    @ConditionalOnProperty(prefix = "cloud-native", value = "gateway-routing", havingValue = "true", matchIfMissing = true)
    public CloudFeignRequestInterceptor cloudFeignRequestInterceptor() {
        return new CloudFeignRequestInterceptor();
    }

    @Configuration(proxyBeanMethods = false)
    @Conditional(CloudNativeCondition.class)
    public class CloudNativeRegisterAutoConfiguration {

        @Bean
        public CloudNativeServiceRegistry getServiceRegistry(KubernetesRegistryClient kubernetesRegistryClient) {
            return new CloudNativeServiceRegistry(kubernetesRegistryClient);
        }

        @Bean
        public CloudNativeRegistration getRegistration() {
            return new CloudNativeRegistration();
        }

        @Bean
        public CloudNativeAutoServiceRegistration kubernetesAutoServiceRegistration(ApplicationContext context, CloudNativeServiceRegistry registry, CloudNativeRegistration registration) {
            System.out.println("register start....");
            return new CloudNativeAutoServiceRegistration(context, registry, registration);
        }
    }
}




