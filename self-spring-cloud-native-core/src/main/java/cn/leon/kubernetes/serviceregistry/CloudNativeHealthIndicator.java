package cn.leon.kubernetes.serviceregistry;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

public class CloudNativeHealthIndicator extends AbstractHealthIndicator{


    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {

    }
}
