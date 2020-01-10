package cn.leon.gateway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
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
public class DynamicRouteServiceImpl extends ApplicationContextEvent {

    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationContext applicationContext;

    /**
     * Create a new ContextStartedEvent.
     *
     * @param source the {@code ApplicationContext} that the event is raised for
     *               (must not be {@code null})
     */
    public DynamicRouteServiceImpl(ApplicationContext source) {
        super(source);
    }

    /**
     * 添加路由实体类
     *
     * @param definition
     * @return
     */
    public boolean add(RouteDefinition definition) {
        routeDefinitionWriter.save((Mono<RouteDefinition>) Mono.just(definition).subscribe());
        applicationContext.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }

    /**
     * @param definition 路由实体类
     * @return
     */
    public boolean update(RouteDefinition definition) {
        try {
            routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            log.error("update 失败。没有找到对应的路由ID :{}", definition.getId());
        }

        routeDefinitionWriter.save((Mono<RouteDefinition>) (Mono.just(definition)).subscribe());
        applicationContext.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }

    /**
     * serviceId
     *
     * @param id
     * @return
     */
    public boolean del(String id) {
        routeDefinitionWriter.delete(Mono.just(id));
        applicationContext.publishEvent(new RefreshRoutesEvent(this));
        return true;
    }
}
