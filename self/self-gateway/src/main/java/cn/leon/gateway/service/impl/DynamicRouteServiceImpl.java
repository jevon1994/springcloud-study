package cn.leon.gateway.service.impl;

import cn.leon.gateway.service.DynamicRouteService;
import cn.leon.gateway.service.GatewayRoutesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @ClassName DynamicRouteServiceImpl
 * @Description
 * @Author Jevon
 * @Date2020/1/10 15:28
 **/
@Slf4j
@Service
public class DynamicRouteServiceImpl implements DynamicRouteService {

    @Resource
    private GatewayRoutesService gatewayRoutesService;

    /**
     * 添加路由实体类
     *
     * @param definition
     * @return
     */
    @Override
    public boolean add(RouteDefinition definition) {
        gatewayRoutesService.save(Mono.just(definition)).subscribe();
        return true;
    }

    /**
     * @param definition 路由实体类
     * @return
     */
    @Override
    public boolean update(RouteDefinition definition) {
        try {
            gatewayRoutesService.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            log.error("update 失败。没有找到对应的路由ID :{}", definition.getId());
        }
        gatewayRoutesService.save((Mono<RouteDefinition>) (Mono.just(definition)).subscribe());
        return true;
    }

    /**
     * serviceId
     *
     * @param id
     * @return
     */
    @Override
    public boolean del(String id) {
        gatewayRoutesService.delete(Mono.just(id));
        return true;
    }
}
