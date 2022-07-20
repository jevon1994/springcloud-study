package cn.leon.bussiness
        ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.HashMap;

/**
 * @author jimin.jm@alibaba-inc.com
 * @date 2019/06/14
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }
}
