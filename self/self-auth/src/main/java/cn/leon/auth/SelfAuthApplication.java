package cn.leon.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class SelfAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfAuthApplication.class, args);
    }

}
