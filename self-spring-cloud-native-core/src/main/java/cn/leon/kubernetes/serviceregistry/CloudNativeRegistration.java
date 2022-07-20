package cn.leon.kubernetes.serviceregistry;

import org.springframework.cloud.client.serviceregistry.Registration;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author mujian
 * @Classname CloudNativeRegistration
 * @Description
 * @Date 2021/12/27
 */

public class CloudNativeRegistration implements Registration, Closeable {


    private AtomicBoolean running = new AtomicBoolean(false);


    @Override
    public void close() throws IOException {
    }

    @Override
    public String getServiceId() {
        return "kubernetes";
    }

    @Override
    public String getHost() {
        return "localhost";
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public URI getUri() {
        return null;
    }

    @Override
    public Map<String, String> getMetadata() {
        return null;
    }

    @Override
    public String toString() {
        return "KubernetesRegistration{" + "client=" + ", properties="
                + ", running=" + this.running + '}';
    }

}
