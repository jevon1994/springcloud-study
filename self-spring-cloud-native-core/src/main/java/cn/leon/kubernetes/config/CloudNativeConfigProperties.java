package cn.leon.kubernetes.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cloud.config")
public class CloudNativeConfigProperties {
    private String server;
    private Boolean enabled;
}
