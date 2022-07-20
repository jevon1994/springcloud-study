package cn.leon.bussiness.client;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

//@Slf4j
@FeignClient(value = "order-service"
        ,url = "http://localhost:8082/"
)
public interface OrderClient {

    @GetMapping("/api/order/debit")
    void create(@RequestHeader("Content-length") Integer contentLength, @RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode, @RequestParam("count") Integer orderCount);
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public void create(String userId, String commodityCode, int orderCount) {
//        String url = "http://127.0.0.1:8082/api/order/debit?userId=" + userId + "&commodityCode=" + commodityCode + "&count=" + orderCount;
//        try {
//            restTemplate.getForEntity(url, Void.class);
//        } catch (Exception e) {
//            log.error("create url {} ,error:", url);
//            throw new RuntimeException();
//        }
//    }
}
