package cn.leon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * <p>@EnableEurekaServer，启动一个服务注册中心</p>
 */
@EnableEurekaServer
@SpringBootApplication
public class ClusterEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClusterEurekaServerApplication.class, args);
    }
}
