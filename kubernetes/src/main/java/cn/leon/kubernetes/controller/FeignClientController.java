package cn.leon.kubernetes.controller;

import cn.leon.kubernetes.feign.AccessServiceClient;
import cn.leon.kubernetes.feign.FeignClientApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FeignClientController implements FeignClientApi {

    @Autowired
    private AccessServiceClient accessServiceClient;

//    @GetMapping("invoke")
    @Override
    public void invoke() {
        accessServiceClient.test();
    }
}
