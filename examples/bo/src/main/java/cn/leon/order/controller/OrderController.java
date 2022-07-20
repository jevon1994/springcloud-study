package cn.leon.order.controller;

import cn.leon.order.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/order")
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping(value = "/debit")
    public void debit(@RequestHeader Map<String, String> headers, @RequestParam String userId, @RequestParam String commodityCode, @RequestParam Integer count) {
        System.out.println(headers);
        orderService.create(userId, commodityCode, count);
    }


    @GetMapping(value = "/mock")
    public void mockOrder() throws JsonProcessingException {
        orderService.saveOrder();
    }
}
