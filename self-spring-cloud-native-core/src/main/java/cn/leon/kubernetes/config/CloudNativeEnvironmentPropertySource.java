package cn.leon.kubernetes.config;

import cn.leon.kubernetes.enums.RegisterEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class CloudNativeEnvironmentPropertySource implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String property = environment.getProperty("cloud-native.discovery.type");
        if (RegisterEnum.KUBERNETES.name().toLowerCase().equals(property)) {
            Properties properties = new Properties();
            properties.setProperty("spring.autoconfigure.exclude", "org.springframework.cloud.netflix.eureka.config.EurekaClientConfigServerAutoConfiguration");
            List<String> list = Arrays.asList("org.springframework.cloud.netflix.eureka.config.EurekaClientConfigServerAutoConfiguration",
                    "org.springframework.cloud.netflix.eureka.config.DiscoveryClientOptionalArgsConfiguration",
                    "org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration",
                    "org.springframework.cloud.netflix.ribbon.eureka.RibbonEurekaAutoConfiguration",
                    "org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration",
                    "org.springframework.cloud.netflix.eureka.reactive.EurekaReactiveDiscoveryClientConfiguration",
                    "org.springframework.cloud.netflix.eureka.loadbalancer.LoadBalancerEurekaAutoConfiguration");
            properties.put("spring.autoconfigure.exclude", list);
            PropertiesPropertySource propertySource = new PropertiesPropertySource("spring.autoconfigure.exclude", properties);
            environment.getPropertySources().addLast(propertySource);
        }
    }
}
