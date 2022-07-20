package cn.leon.kubernetes.config;

import cn.leon.kubernetes.feign.ConfigServiceClient;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.cloud.bootstrap.support.OriginTrackedCompositePropertySource;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mujian
 * @Classname ConfigInitializableImpl
 * @Description
 * @Date 2022/1/26
 */
public class ConfigInitializableImpl implements ConfigInitializable {

    private ConfigServiceClient configServiceClient;
    private String name;
    private String profile;
    private String label;

    private final static ThreadLocal<CompositePropertySource> config = new ThreadLocal<>();


    public ConfigInitializableImpl(ConfigServiceClient configServiceClient, String name, String profile, String label) {
        this.configServiceClient = configServiceClient;
        this.name = name;
        this.profile = profile;
        this.label = label;
    }


    @Override
    public CompositePropertySource getConfig() {
        CompositePropertySource compositePropertySource = config.get();
        config.remove();
        return compositePropertySource;
    }

    @Override
    public String getDiscoveryType() {
        String discoveryType = null;
        Environment environment = StringUtils.hasText(label) ? configServiceClient.getConfigByLabel(name, profile, label) : configServiceClient.getConfig(name, profile);
        CompositePropertySource composite = new OriginTrackedCompositePropertySource("configService");
        if (environment != null) {
            // result.getPropertySources() can be null if using xml
            if (environment.getPropertySources() != null) {
                for (org.springframework.cloud.config.environment.PropertySource source : environment.getPropertySources()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = translateOrigins(source.getName(),
                            (Map<String, Object>) source.getSource());

                    Object type = map.get("cloud-native.discovery.type");
                    if (type != null && ConfigInitializable.configProcessor.getAndIncrement() == 0) {
                        System.out.println("------------- {} " + source + ", type : {}" + type);
                        discoveryType = type.toString();
                    }

                    composite.addPropertySource(
                            new OriginTrackedMapPropertySource(source.getName(),
                                    map));
                }
            }

            if (StringUtils.hasText(environment.getState())
                    || StringUtils.hasText(environment.getVersion())) {
                HashMap<String, Object> map = new HashMap<>();
                putValue(map, "config.client.state", environment.getState());
                putValue(map, "config.client.version", environment.getVersion());
                composite.addFirstPropertySource(
                        new MapPropertySource("configClient", map));
            }
            config.set(composite);
        }
        return discoveryType;
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
