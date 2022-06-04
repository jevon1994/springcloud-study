package cn.leon.mq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.stream.binder.rabbit.properties.RabbitProducerProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloud.stream")
public class CloudStreamConfig {

    private BrokerProperties broker;
    private Map<String, BindingProperties> bindings;

    @Data
    public static class BrokerProperties {
        private String address;
        private String username;
        private String password;
        private String type;
    }

    @Data
    public static class BindingProperties {
        private ProducerProperties consumer;
    }

    @Data
    public static class ProducerProperties extends RabbitProducerProperties {
    }
}
