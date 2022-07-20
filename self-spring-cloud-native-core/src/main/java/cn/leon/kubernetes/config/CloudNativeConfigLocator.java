package cn.leon.kubernetes.config;

import cn.leon.kubernetes.feign.ConfigServiceClient;
import cn.leon.kubernetes.model.CloudNativeProperties;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import java.util.*;

/**
 * @author mujian
 * @Classname CloudNativeConfigLocator
 * @Description
 * @Date 2022/1/26
 */
public class CloudNativeConfigLocator extends ConfigServicePropertySourceLocator {

    private ConfigServiceClient configServiceClient;
    private CloudNativeProperties cloudNativeProperties;

    public CloudNativeConfigLocator(ConfigClientProperties defaultProperties, ConfigServiceClient configServiceClient, CloudNativeProperties cloudNativeProperties) {
        super(defaultProperties);
        this.configServiceClient = configServiceClient;
        this.cloudNativeProperties = cloudNativeProperties;
    }

    /**
     * extend get config with gateway
     *
     * @param environment
     * @return
     */
    @Override
    public PropertySource<?> locate(Environment environment) {


        String configEnabled = environment.getProperty("cloud-native.config.enabled");
        if ("false".equals(configEnabled)) {
            return null;
        }


        // Try all the labels until one works
        CloudNativeProperties.ConfigProperties config = cloudNativeProperties.getConfig();
        ConfigInitializable confingInitialize = new ConfigInitializableImpl(configServiceClient, config.getName(), config.getProfile(), config.getLabel());

        return confingInitialize.getConfig();
    }

    @Override
    // @Retryable(interceptor = "configServerRetryInterceptor")
    public Collection<PropertySource<?>> locateCollection(
            Environment environment) {
        PropertySource<?> propertySource = this.locate(environment);
        if (propertySource == null) {
            return Collections.emptyList();
        }
        if (CompositePropertySource.class.isInstance(propertySource)) {
            Collection<PropertySource<?>> sources = ((CompositePropertySource) propertySource)
                    .getPropertySources();
            List<PropertySource<?>> filteredSources = new ArrayList<>();
            for (PropertySource<?> p : sources) {
                if (p != null) {
                    filteredSources.add(p);
                }
            }
            return filteredSources;
        } else {
            return Arrays.asList(propertySource);
        }
    }
}

