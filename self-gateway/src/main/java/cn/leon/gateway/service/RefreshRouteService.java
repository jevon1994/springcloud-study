package cn.leon.gateway.service;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

/**
 * @ClassName RefreshRouteService
 * @Description
 * @Author Jevon
 * @Date2020/1/9 17:18
 **/
@Component
public class RefreshRouteService extends ApplicationContextEvent {

    private ApplicationContext context;

    /**
     * Create a new ContextStartedEvent.
     *
     * @param source the {@code ApplicationContext} that the event is raised for
     *               (must not be {@code null})
     */
    public RefreshRouteService(ApplicationContext source) {
        super(source);
    }

    /**
     * 刷新路由表
     */
    public void refreshRoutes() {
        context.publishEvent(new RefreshRoutesEvent(this));
    }
}
