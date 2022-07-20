package cn.leon.kubernetes.config;

import cn.leon.kubernetes.decoder.CloudNativeConfigDecoder;
import cn.leon.kubernetes.enums.RegisterEnum;
import cn.leon.kubernetes.feign.ConfigServiceClient;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author mujian
 * @Classname CloudNativeEnvironmentPropertySource
 * @Description
 * @Date 2022/1/26
 */
@Slf4j
public class CloudNativeEnvironmentPropertySource implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (ConfigInitializable.envProcessor.getAndIncrement() != 1) {
            return;
        }
        Properties properties = new Properties();

        // origin or default all
        String discoveryType = getDiscoveryType(environment);
        properties.put("cloud-native.discovery.type", discoveryType);
        List<String> excludes = new ArrayList<>();
        if (RegisterEnum.KUBERNETES.name().toLowerCase().equals(discoveryType)) {
            excludes.add("org.springframework.cloud.netflix.eureka.config.EurekaClientConfigServerAutoConfiguration");
            excludes.add("org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration");
            excludes.add("org.springframework.cloud.netflix.ribbon.eureka.RibbonEurekaAutoConfiguration");
            excludes.add("org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration");
            excludes.add("org.springframework.cloud.netflix.eureka.reactive.EurekaReactiveDiscoveryClientConfiguration");
            excludes.add("org.springframework.cloud.netflix.eureka.loadbalancer.LoadBalancerEurekaAutoConfiguration");
            excludes.add("org.springframework.cloud.netflix.eureka.config.EurekaClientConfigServerAutoConfiguration");
        }
        excludes.add("org.springframework.cloud.kubernetes.KubernetesAutoConfiguration");
        properties.put("spring.autoconfigure.exclude", excludes);
        PropertiesPropertySource propertySource = new PropertiesPropertySource("configservice", properties);
        environment.getPropertySources().addLast(propertySource);

    }

    private String getDiscoveryType(ConfigurableEnvironment environment) {
        String url = environment.getProperty("cloud-native.config.url");
        String name = environment.getProperty("cloud-native.config.name");
        String profile = environment.getProperty("cloud-native.config.profile");
        String label = environment.getProperty("cloud-native.config.label");
        String configEnable = environment.getProperty("cloud-native.config.enabled");
        String localType = environment.getProperty("cloud-native.discovery.type");
        if ("false".equals(configEnable)) {
            return StringUtils.hasText(localType) ? localType : "all";
        }
        ConfigServiceClient configServiceClient = Feign.builder()
                .decoder(new CloudNativeConfigDecoder())
                .contract(new SpringMvcContract())
                .target(ConfigServiceClient.class, url);
        ConfigInitializable configInitializable = new ConfigInitializableImpl(configServiceClient, name, profile, label);
        String originType = configInitializable.getDiscoveryType();
        String discoveryType = originType == null ? localType : originType;
        if (discoveryType == null) {
            discoveryType = "all";
        }
        System.out.println("--------------- Discovery type " + discoveryType);
        return discoveryType;
    }
}
