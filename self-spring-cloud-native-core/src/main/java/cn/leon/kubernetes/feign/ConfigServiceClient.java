package cn.leon.kubernetes.feign;

import org.springframework.cloud.config.environment.Environment;
import org.springframework.web.bind.annotation.GetMapping;

//@FeignClient(name = "cloud-config", url = "http://localhost:9000")
public interface ConfigServiceClient {

    @GetMapping("kubernetes/dev")
    Environment getConfig();
}
