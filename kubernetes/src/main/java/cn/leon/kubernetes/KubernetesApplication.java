package cn.leon.kubernetes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@EnableFeignClients
@SpringBootApplication
public class KubernetesApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(KubernetesApplication.class, args);
        System.out.println(run.getEnvironment().getProperty("spring.autoconfigure.exclude"));
    }
}
