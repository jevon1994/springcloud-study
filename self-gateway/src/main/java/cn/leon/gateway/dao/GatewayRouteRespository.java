package cn.leon.gateway.dao;

import cn.leon.gateway.constant.CommonConstant;
import cn.leon.gateway.model.GatewayRouteDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GatewayRouteDefinition
 * @Description
 * @Author Jevon
 * @Date2020/1/8 16:25
 **/
@Slf4j
@Component
public class GatewayRouteRespository implements RouteDefinitionRepository {
    @Resource
    private RouteDefinitionWriter writer;
    private ApplicationContext applicationContext;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            GatewayRouteDefinition vo = new GatewayRouteDefinition();
            BeanUtils.copyProperties(r, vo);
            log.info("保存路由信息{}", vo);
            redisTemplate.opsForHash().put(CommonConstant.ROUTE_KEY, r.getId(), vo);
            applicationContext.publishEvent(new RefreshRoutesEvent(this));
            return Mono.empty();
        });
    }
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        routeId.subscribe(id -> {
            log.info("删除路由信息{}", id);
            redisTemplate.opsForHash().delete(CommonConstant.ROUTE_KEY, id);
        });
        applicationContext.publishEvent(new RefreshRoutesEvent(this));
        return Mono.empty();
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(GatewayRouteDefinition.class));
        List<GatewayRouteDefinition> values = redisTemplate.opsForHash().values(CommonConstant.ROUTE_KEY);
        List<RouteDefinition> definitionList = new ArrayList<>();
        values.forEach(vo -> {
            RouteDefinition routeDefinition = new RouteDefinition();
            BeanUtils.copyProperties(vo, routeDefinition);
            definitionList.add(routeDefinition);
        });
        log.debug("redis 中路由定义条数： {}， {}", definitionList.size(), definitionList);
        return Flux.fromIterable(definitionList);
    }
}
