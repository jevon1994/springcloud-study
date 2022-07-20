package cn.leon.kubernetes.decoder;

import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mujian
 * @Classname CloudNativeRegisterConfiguration
 * @Description
 * @Date 2022/2/11
 */
@Configuration(proxyBeanMethods = false)
public class CloudNativeRegisterConfiguration {

    @Bean
    public Decoder cloudNativeRegisterDecoder() {
        return new CloudNativeRegisterDecoder();
    }
}
