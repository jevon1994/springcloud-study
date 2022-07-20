package cn.leon.kubernetes.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author mujian
 * @Classname WowjoyCloudNativeProperties
 * @Description
 * @Date 2022/2/9
 */
@Getter
@Setter
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "wowjoy.cloud-native")
public class CloudNativeProperties {

    private ConfigProperties config;

    private DiscoveryProperties discovery;

    private GatewayProperties gateway;

    @Builder.Default
    private boolean debug = false;

    @Getter
    @Setter
    public static class ConfigProperties {
        @Builder.Default
        private String url = "http://ms-config";

        @Builder.Default
        private boolean enabled = false;

        @Builder.Default
        private String name = "${spring.application.name}";

        private String profile;
        private String label;
    }

    @Getter
    @Setter
    public static class DiscoveryProperties {

        private String url;

        @Builder.Default
        private String type = "all";

        private Port[] ports;
    }

    @Getter
    @Setter
    public static class GatewayProperties {

        @Builder.Default
        private boolean routing = true;

        @Builder.Default
        private String prefix = "http://cgw/";
    }
}
