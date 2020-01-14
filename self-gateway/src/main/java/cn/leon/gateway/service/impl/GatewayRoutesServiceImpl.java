package cn.leon.gateway.service.impl;

import cn.leon.gateway.domain.dto.GatewayRouteDefinition;
import cn.leon.gateway.domain.entity.GatewayRoutesEntity;
import cn.leon.gateway.mapper.GatewayRoutesMapper;
import cn.leon.gateway.service.GatewayRoutesService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName GatewayRouteDefinition
 * @Description
 * @Author Jevon
 * @Date2020/1/8 16:25
 **/
@Slf4j
@Service
public class GatewayRoutesServiceImpl implements GatewayRoutesService {
    @Autowired
    private GatewayRoutesMapper repo;

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            GatewayRoutesEntity entity = GatewayRoutesEntity.builder()
                    .filters(parseObjectToJson(r.getFilters()))
                    .predicates(parseObjectToJson(r.getPredicates()))
                    .serviceId(r.getId())
                    .order(r.getOrder())
                    .uri(r.getUri().toString())
                    .build();
            log.info("保存路由信息{}", entity);
            repo.insert(entity);
            return Mono.empty();
        });
    }

    private String parseObjectToJson(Collection collection) {
        return CollectionUtils.isEmpty(collection) ? null : JSON.toJSONString(collection);
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        routeId.subscribe(id -> {
            repo.deleteById(id);
            log.info("删除路由信息{}", id);
        });
        return Mono.empty();
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<GatewayRouteDefinition> gatewayRouteDefinitionList = repo.selectAll();
        if (CollectionUtils.isEmpty(gatewayRouteDefinitionList)) {
            return Flux.empty();
        }
        List<RouteDefinition> routeDefinitionList = new ArrayList();
        gatewayRouteDefinitionList.forEach(s -> {
            RouteDefinition routeDefinition = new RouteDefinition();
            List<PredicateDefinition> predicateDefinitionList = JSONArray.parseArray(s.getPredicates(), PredicateDefinition.class);
            List<FilterDefinition> filterDefinitionList = JSONArray.parseArray(s.getFilters(), FilterDefinition.class);
            if (!CollectionUtils.isEmpty(predicateDefinitionList)) {
                routeDefinition.setPredicates(predicateDefinitionList);
            }
            if (!CollectionUtils.isEmpty(filterDefinitionList)) {
                routeDefinition.setFilters(filterDefinitionList);
            }
            routeDefinition.setId(s.getServiceId());
            routeDefinition.setUri(URI.create(s.getUri()));
            routeDefinitionList.add(routeDefinition);
        });
        return Flux.fromIterable(routeDefinitionList);
    }
}
