package cn.leon.examples.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:rocketmq-config.yml")
public class MqConfiguration {
}
