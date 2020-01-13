package cn.leon.gateway.service;

import cn.leon.gateway.dao.GatewayRouteRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @ClassName DynamicRouteServiceImpl
 * @Description
 * @Author Jevon
 * @Date2020/1/10 15:28
 **/
@Slf4j
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    @Autowired
    private GatewayRouteRespository gatewayRouteRespository;
    
    private ApplicationEventPublisher publisher;

    /**
     * 添加路由实体类
     *
     * @param definition
     * @return
     */
    public boolean add(RouteDefinition definition) {
        gatewayRouteRespository.save(Mono.just(definition)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }

    /**
     * @param definition 路由实体类
     * @return
     */
    public boolean update(RouteDefinition definition) {
        try {
            gatewayRouteRespository.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            log.error("update 失败。没有找到对应的路由ID :{}", definition.getId());
        }

        gatewayRouteRespository.save((Mono<RouteDefinition>) (Mono.just(definition)).subscribe());
        publisher.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }

    /**
     * serviceId
     *
     * @param id
     * @return
     */
    public boolean del(String id) {
        gatewayRouteRespository.delete(Mono.just(id));
        publisher.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
