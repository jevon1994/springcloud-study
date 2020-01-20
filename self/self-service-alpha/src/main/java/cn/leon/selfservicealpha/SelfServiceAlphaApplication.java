package cn.leon.selfservicealpha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.SpringCloudApplication;

@EnableOAuth2Sso
@SpringCloudApplication
public class SelfServiceAlphaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfServiceAlphaApplication.class, args);
    }

}
