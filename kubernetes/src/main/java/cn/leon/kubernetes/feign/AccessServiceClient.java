package cn.leon.kubernetes.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "cmdb", url= "cmdb")
public interface AccessServiceClient {

    @GetMapping
    void test();
}
