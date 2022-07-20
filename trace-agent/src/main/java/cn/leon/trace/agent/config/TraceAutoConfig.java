package cn.leon.trace.agent.config;

import cn.leon.trace.agent.FeignRequestInterceptor;
import cn.leon.trace.agent.MvcInterceptor;
import cn.leon.trace.agent.RestTemplateInterceptor;
import cn.leon.trace.agent.TraceFeignClientDecorator;
import feign.Client;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class TraceAutoConfig implements WebMvcConfigurer {

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MvcInterceptor(appName)).addPathPatterns("/**");
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Arrays.asList(new RestTemplateInterceptor(appName)));
        return restTemplate;
    }

    @Bean
    @ConditionalOnClass(RequestInterceptor.class)
    public FeignRequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor(appName);
    }

    @Bean
    @ConditionalOnClass({CachingSpringLoadBalancerFactory.class,SpringClientFactory.class})
    public Client feignClient(CachingSpringLoadBalancerFactory cachingFactory,
                              SpringClientFactory clientFactory) {
        return new LoadBalancerFeignClient(new TraceFeignClientDecorator(new Client.Default(null, null)),
                cachingFactory, clientFactory);
    }
}
