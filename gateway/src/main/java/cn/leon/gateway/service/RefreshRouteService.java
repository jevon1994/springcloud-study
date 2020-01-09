package cn.leon.gateway.service;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName RefreshRouteService
 * @Description
 * @Author Jevon
 * @Date2020/1/9 17:18
 **/
@Component
public class RefreshRouteService implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * 刷新路由表
     */
    public void refreshRoutes() {
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
