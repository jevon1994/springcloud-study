package cn.leon.gateway.service.impl;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * @ClassName RefreshRouteService
 * @Description
 * @Author Jevon
 * @Date2020/1/9 17:18
 **/
@Service
public class RefreshRouteService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    /**
     * 刷新路由表
     */
    public void refreshRoutes() {
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
