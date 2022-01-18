package cn.leon.kubernetes.controller;

import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


@Endpoint(id = "shutdown", enableByDefault = false)
public class ShutdownEndpoint implements ApplicationContextAware {

    private ApplicationContext context;

    @WriteOperation
    public void shutdown() {
        Thread thread = new Thread(this::performShutdown);
        thread.setContextClassLoader(getClass().getClassLoader());
        thread.start();
    }

    private void performShutdown() {
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        // 此处close 逻辑和上边 shutdownhook 的处理一样
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}