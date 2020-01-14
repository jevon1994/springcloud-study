package cn.leon.gateway.service;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @ClassName GatewayRouteDefinition
 * @Description
 * @Author Jevon
 * @Date2020/1/8 16:25
 **/
public interface GatewayRoutesService extends RouteDefinitionRepository {
    @Override
    Mono<Void> save(Mono<RouteDefinition> route);

    @Override
    Mono<Void> delete(Mono<String> routeId);

    @Override
    Flux<RouteDefinition> getRouteDefinitions();
}
