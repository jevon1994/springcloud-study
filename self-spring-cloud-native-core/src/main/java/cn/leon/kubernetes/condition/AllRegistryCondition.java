package cn.leon.kubernetes.condition;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;

/**
 * @author mujian
 * @Classname CloudNativeCondition
 * @Description
 * @Date 2022/1/26
 */
public class AllRegistryCondition extends NoneNestedConditions {


    public AllRegistryCondition() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @ConditionalOnProperty(prefix = "cloud-native.discovery", value = "type", havingValue = "eureka")
    public static class WithoutEurekaCondition {
    }

}
