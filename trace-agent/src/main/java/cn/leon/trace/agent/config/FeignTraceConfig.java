package cn.leon.trace.agent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConditionalOnClass(FeignClient.class)
public class FeignTraceConfig {

    @Value("${spring.application.name}")
    private String appName;

//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return new FeignRequestInterceptor(appName);
//    }
//
//    @Bean
//    public Client feignClient(CachingSpringLoadBalancerFactory cachingFactory,
//                              SpringClientFactory clientFactory) {
//        return new LoadBalancerFeignClient(new TraceFeignClientDecorator(new Client.Default(null, null)),
//                cachingFactory, clientFactory);
//    }
}
