package cn.leon.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Slf4j
public class CloudStreamEnvironmentPropertySource implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Properties properties = new Properties();
        List<String> objects = Arrays.asList(
                "org.springframework.cloud.stream.binder.kafka.config.ExtendedBindingHandlerMappingsProviderConfiguration");
        properties.put("spring.autoconfigure.exclude", objects);
        PropertiesPropertySource propertySource = new PropertiesPropertySource("spring.autoconfigure.exclude", properties);
        environment.getPropertySources().addLast(propertySource);
    }
}
