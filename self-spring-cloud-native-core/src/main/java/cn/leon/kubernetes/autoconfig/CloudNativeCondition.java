package cn.leon.kubernetes.autoconfig;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class CloudNativeCondition extends AnyNestedCondition {

    public CloudNativeCondition() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @ConditionalOnProperty(name = "cloud-native.discovery.type", havingValue = "kubernetes")
    public static class KubernetesCondition {
    }
}
