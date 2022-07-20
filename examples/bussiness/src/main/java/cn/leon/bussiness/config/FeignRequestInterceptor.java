package cn.leon.bussiness.config;

import cn.leon.bussiness.client.StorageClient;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

public class FeignRequestInterceptor implements RequestInterceptor {

    @Autowired
    private StorageClient storageClient;


    @Override
    public void apply(RequestTemplate template) {
        System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().getId());
//        storageClient.deduct("111", 1);
    }
}
