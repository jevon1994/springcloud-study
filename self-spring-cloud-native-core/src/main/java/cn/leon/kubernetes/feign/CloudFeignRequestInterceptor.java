package cn.leon.kubernetes.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Target;

public class CloudFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        Target<?> target = template.feignTarget();
//                Class<?> aClass = template.methodMetadata().targetType();
//                FeignClient annotation = aClass.getAnnotation(FeignClient.class);
//                System.out.println(annotation.name());
//        if ("cloud-gateway".equals(target.name())) return;
//        String newUrl = "http://NEW";
//        template.target(newUrl);
    }
}
