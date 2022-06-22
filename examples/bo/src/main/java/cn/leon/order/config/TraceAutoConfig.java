package cn.leon.trace.agent;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public RequestInterceptor requestInterceptor() {
        return new FeignRequestInterceptor(appName);
    }
}
