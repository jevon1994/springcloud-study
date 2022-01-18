package cn.leon.kubernetes.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Target;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudFeignClientConfig {

    @Bean
    public RequestInterceptor cloudContextInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
//                Target<?> target = template.feignTarget();
//                if("cloud-gateway".equals(target.name())) return;
//                String newUrl = "http://NEW";
//                template.target(newUrl);
            }
        };
    }
}
