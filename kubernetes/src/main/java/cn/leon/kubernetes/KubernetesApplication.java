package cn.leon.kubernetes;

import cn.leon.kubernetes.feign.ConfigServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class KubernetesApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KubernetesApplication.class, args);
    }

    @Autowired
    private ConfigServiceClient configServiceClient;

    @Override
    public void run(String... args) throws Exception {
    }
}
