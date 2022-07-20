package cn.leon.kubernetes.condition;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * @author mujian
 * @Classname CloudNativeCondition
 * @Description
 * @Date 2022/1/26
 */
public class CloudNativeCondition extends AnyNestedCondition {

    public CloudNativeCondition() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    public CloudNativeCondition(ConfigurationPhase configurationPhase) {
        super(configurationPhase);
    }

    @ConditionalOnProperty(name = "cloud-native.discovery.type", havingValue = "kubernetes")
    public static class KubernetesCondition {
    }

}
