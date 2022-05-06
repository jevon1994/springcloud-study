package cn.leon.kubernetes.config;

import cn.leon.kubernetes.feign.ConfigServiceClient;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.cloud.bootstrap.support.OriginTrackedCompositePropertySource;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SelfLocator extends ConfigServicePropertySourceLocator implements ConfigLocator {

    private ConfigServiceClient configServiceClient;

    public SelfLocator(ConfigClientProperties defaultProperties, ConfigServiceClient configServiceClient) {
        super(defaultProperties);
        this.configServiceClient = configServiceClient;
    }

    /**
     * extend get config with gateway
     * @param environment
     * @return
     */
    @Override
    public PropertySource<?> locate(Environment environment) {
        CompositePropertySource composite = new OriginTrackedCompositePropertySource("configService");
        // Try all the labels until one works
        ConfingInitialize confingInitialize = new ConfingInitializeImpl(configServiceClient);
        org.springframework.cloud.config.environment.Environment result = confingInitialize.getConfig();
        if (result != null) {
            System.out.println(result);
            // result.getPropertySources() can be null if using xml
            if (result.getPropertySources() != null) {
                for (org.springframework.cloud.config.environment.PropertySource source : result.getPropertySources()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = translateOrigins(source.getName(),
                            (Map<String, Object>) source.getSource());
                    composite.addPropertySource(
                            new OriginTrackedMapPropertySource(source.getName(),
                                    map));
                }
            }

            if (StringUtils.hasText(result.getState())
                    || StringUtils.hasText(result.getVersion())) {
                HashMap<String, Object> map = new HashMap<>();
                putValue(map, "config.client.state", result.getState());
                putValue(map, "config.client.version", result.getVersion());
                composite.addFirstPropertySource(
                        new MapPropertySource("configClient", map));
            }
            return composite;
        }
        System.out.println("config start.......");
//        PropertySource<?> locate = super.locate(environment);
        return composite;
    }

    private Map<String, Object> translateOrigins(String name,
                                                 Map<String, Object> source) {
        Map<String, Object> withOrigins = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            boolean hasOrigin = false;

            if (entry.getValue() instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> value = (Map<String, Object>) entry.getValue();
                if (value.size() == 2 && value.containsKey("origin")
                        && value.containsKey("value")) {
                    Origin origin = new ConfigServiceOrigin(name, value.get("origin"));
                    OriginTrackedValue trackedValue = OriginTrackedValue
                            .of(value.get("value"), origin);
                    withOrigins.put(entry.getKey(), trackedValue);
                    hasOrigin = true;
                }
            }

            if (!hasOrigin) {
                withOrigins.put(entry.getKey(), entry.getValue());
            }
        }
        return withOrigins;
    }

    static class ConfigServiceOrigin implements Origin {

        private final String remotePropertySource;

        private final Object origin;

        ConfigServiceOrigin(String remotePropertySource, Object origin) {
            this.remotePropertySource = remotePropertySource;
            Assert.notNull(origin, "origin may not be null");
            this.origin = origin;

        }

        @Override
        public String toString() {
            return "Config Server " + this.remotePropertySource + ":"
                    + this.origin.toString();
        }

    }

    private void putValue(HashMap<String, Object> map, String key, String value) {
        if (StringUtils.hasText(value)) {
            map.put(key, value);
        }
    }
}
