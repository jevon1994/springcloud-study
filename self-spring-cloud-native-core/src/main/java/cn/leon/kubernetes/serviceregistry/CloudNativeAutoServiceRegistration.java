package cn.leon.kubernetes.serviceregistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mujian
 * @Classname CloudNativeAutoServiceRegistration
 * @Description
 * @Date 2021/12/27
 */
public class CloudNativeAutoServiceRegistration
        implements AutoServiceRegistration, SmartLifecycle, Ordered {

    private static final Log log = LogFactory
            .getLog(CloudNativeAutoServiceRegistration.class);

    private AtomicBoolean running = new AtomicBoolean(false);

    private int order = 0;

    private AtomicInteger port = new AtomicInteger(0);

    private ApplicationContext context;

    private CloudNativeServiceRegistry serviceRegistry;

    private CloudNativeRegistration registration;

    public CloudNativeAutoServiceRegistration(ApplicationContext context,
                                              CloudNativeServiceRegistry serviceRegistry,
                                              CloudNativeRegistration registration) {
        this.context = context;
        this.serviceRegistry = serviceRegistry;
        this.registration = registration;
    }


    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        stop();
        callback.run();
    }

    @Override
    public void start() {
        if(this.isRunning()){
            return;
        }
        this.serviceRegistry.register(this.registration);

        // System.out.println("publish event");
        this.running.set(true);
    }

    @Override
    public void stop() {
        this.serviceRegistry.deregister(this.registration);
        this.running.set(false);
    }

    @Override
    public boolean isRunning() {
        return this.running.get();
    }

    @Override
    public int getPhase() {
        return 0;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @EventListener(ServletWebServerInitializedEvent.class)
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        int localPort = event.getWebServer().getPort();
        if (this.port.get() == 0) {
            log.info("Updating port to " + localPort);
            this.port.compareAndSet(0, localPort);
            start();
        }
    }

    @EventListener(ContextClosedEvent.class)
    public void onApplicationEvent(ContextClosedEvent event) {
        if (event.getApplicationContext() == this.context) {
            stop();
        }
    }
}
