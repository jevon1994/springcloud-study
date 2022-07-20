package cn.leon.kubernetes.serviceregistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

/**
 * @author mujian
 * @Classname CloudNativeServiceRegistry
 * @Description
 * @Date 2021/12/27
 */

public class CloudNativeServiceRegistry implements ServiceRegistry<CloudNativeRegistration> {
    public static final Logger log = LoggerFactory.getLogger(CloudNativeServiceRegistry.class);
    private CloudNativeRegisterService cloudNativeRegisterService;

    public CloudNativeServiceRegistry(CloudNativeRegisterService cloudNativeRegisterService) {
        this.cloudNativeRegisterService = cloudNativeRegisterService;
    }

    @Override
    public void register(CloudNativeRegistration registration) {
        cloudNativeRegisterService.register();
    }

    @Override
    public void deregister(CloudNativeRegistration registration) {
    }

    @Override
    public void close() {
    }

    @Override
    public void setStatus(CloudNativeRegistration registration, String status) {
        System.out.println("Set Status for : " + registration + " Status: " + status);
    }

    @Override
    public <T> T getStatus(CloudNativeRegistration registration) {
        System.out.println("get status");
        return null;
    }
}

