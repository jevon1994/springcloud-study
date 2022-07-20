package cn.leon.trace.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

public class CloudTracePropertySource implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Properties properties = new Properties();
//        List<String> list = Arrays.asList("org.springframework.cloud.openfeign.ribbon.FeignRibbonClientAutoConfiguration",
//                "org.springframework.cloud.openfeign.FeignAutoConfiguration",
//                "org.springframework.cloud.openfeign.encoding.FeignAcceptGzipEncodingAutoConfiguration",
//                "org.springframework.cloud.openfeign.encoding.FeignContentGzipEncodingAutoConfiguration");
//        properties.put("spring.autoconfigure.exclude", list);
        PropertiesPropertySource propertySource = new PropertiesPropertySource("spring.autoconfigure.exclude", properties);
        environment.getPropertySources().addLast(propertySource);
    }
}
