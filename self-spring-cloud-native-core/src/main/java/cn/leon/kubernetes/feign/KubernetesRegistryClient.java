package cn.leon.kubernetes.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cloud-gateway",url = "http://localhost:8088")
public interface KubernetesRegistryClient {

    @GetMapping("echo")
    String echo(@RequestParam String context);
}
