package cn.leon.kubernetes.feign;

import cn.leon.kubernetes.model.CloudNativeProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.UUID;

/**
 * @auther: mujian
 * @Classname CloudFeignRequestInterceptor
 * @Description
 * @date: 2022/2/17 10:42
 */
public class CloudFeignRequestInterceptor implements RequestInterceptor, ApplicationContextAware {

    public static final Logger log = LoggerFactory.getLogger(CloudFeignRequestInterceptor.class);

    private CloudNativeProperties cloudNativeProperties;
    private ApplicationContext applicationContext;

    public CloudFeignRequestInterceptor(CloudNativeProperties cloudNativeProperties) {
        this.cloudNativeProperties = cloudNativeProperties;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("traceid", UUID.randomUUID().toString());
        // if (template.url().contains("/app/v1/services")) {
        //     return;
        // }
        // String prefix = wowjoyCloudNativeProperties.getGateway().getPrefix();
        // if (wowjoyCloudNativeProperties.isDebug()) {
        //     log.debug("======== gateway routing ========> target: {}", prefix);
        // }
        // template.target(prefix);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

