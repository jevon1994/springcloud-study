package cn.leon.kubernetes;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "cmdb",url = "http://cmdb")
public interface SvcTest {

    @GetMapping
    void test();
}
