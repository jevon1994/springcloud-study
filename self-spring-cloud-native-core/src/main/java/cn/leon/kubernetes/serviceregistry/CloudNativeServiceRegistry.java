package cn.leon.kubernetes.serviceregistry;


import cn.leon.kubernetes.feign.KubernetesRegistryClient;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

public class CloudNativeServiceRegistry implements ServiceRegistry<CloudNativeRegistration> {

    private KubernetesRegistryClient kubernetesRegistryClient;

    public CloudNativeServiceRegistry(KubernetesRegistryClient kubernetesRegistryClient) {
        this.kubernetesRegistryClient = kubernetesRegistryClient;
    }

    @Override
    public void register(CloudNativeRegistration registration) {
//        String registreing = kubernetesRegistryClient.echo("registreing");
//        System.out.println(registreing + " .....");
        System.out.println("registreing .....");

    }

    @Override
    public void deregister(CloudNativeRegistration registration) {
        System.out.println("deregistering ...");
    }

    @Override
    public void close() {
        System.out.println("closing ...");
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
