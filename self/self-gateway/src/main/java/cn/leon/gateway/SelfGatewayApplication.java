package cn.leon.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@ConfigurationPropertiesScan
@MapperScan(value = "cn.leon.gateway.mapper")
public class SelfGatewayApplication {

    public static void main(String[] args) {
        System.out.println(args);
        SpringApplication.run(SelfGatewayApplication.class, args);
    }
}
