package cn.leon.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-client",fallback = NoFoundService.class)
public interface HelloService {

    @GetMapping("/client")
    String hello(@RequestParam(value = "name") String name);
}
