package cn.leon.kubernetes.serviceregistry;

import cn.leon.kubernetes.feign.KubernetesRegistryClient;
import cn.leon.kubernetes.model.RegisterRequest;
import cn.leon.kubernetes.model.CloudNativeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 * @author mujian
 * @Classname CloudNativeRegisterService
 * @Description
 * @Date 2022/2/14
 */
public class CloudNativeRegisterService {

    public static final Logger log = LoggerFactory.getLogger(CloudNativeRegisterService.class);

    private KubernetesRegistryClient kubernetesRegistryClient;

    private Environment environment;
    private CloudNativeProperties cloudNativeProperties;

    public CloudNativeRegisterService(KubernetesRegistryClient kubernetesRegistryClient, Environment environment, CloudNativeProperties cloudNativeProperties) {
        this.kubernetesRegistryClient = kubernetesRegistryClient;
        this.environment = environment;
        this.cloudNativeProperties = cloudNativeProperties;
    }

    public void register() {
        try {
            String appname = environment.getProperty("spring.application.name");
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setPorts(cloudNativeProperties.getDiscovery().getPorts());
            if (cloudNativeProperties.isDebug()) {
                log.info("-------------- Cloud Native App: {} register ----------------------------", appname);
            }
            String res = kubernetesRegistryClient.register(appname, registerRequest);
            if (cloudNativeProperties.isDebug()){
                log.info("-------------- Cloud Native App register success, {} ----------------------------", res);
            }
        } catch (Throwable throwable) {
            log.error("register throw exception, {}", throwable);
        }
    }
}
