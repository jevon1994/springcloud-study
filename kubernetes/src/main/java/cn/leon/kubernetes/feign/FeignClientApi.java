package cn.leon.kubernetes.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "kubernetes",url = "http://localhost")
public interface FeignClientApi {

    @GetMapping("invoke")
    void invoke();
}
