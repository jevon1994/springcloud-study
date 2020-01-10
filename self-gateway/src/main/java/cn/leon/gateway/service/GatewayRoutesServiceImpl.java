package cn.leon.gateway.service;

import cn.leon.gateway.model.GatewayRoutesEntity;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * @ClassName GatewayRoutesServiceImpl
 * @Description
 * @Author Jevon
 * @Date2020/1/10 15:37
 **/
@Slf4j
@Service
public class GatewayRoutesServiceImpl implements GatewayRoutesService {
    public static final String GATEWAY_DEFINE_LIST_KEY = "gateway_routes_list_key";
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationContext context;

    @Override
    public List<GatewayRoutesEntity> findAll() throws Exception {

        Long size = redisTemplate.opsForList().size(GATEWAY_DEFINE_LIST_KEY);
        List<GatewayRoutesEntity> list = redisTemplate.opsForList().range(GATEWAY_DEFINE_LIST_KEY, 0, size);
        return list;
    }

    @Override
    public String loadRouteDefinition() {
        try {
            List<GatewayRoutesEntity> gatewayDefineServiceAll = findAll();
            if (gatewayDefineServiceAll == null) {
                return "none route defined";
            }
            for (GatewayRoutesEntity gatewayDefine : gatewayDefineServiceAll) {
                RouteDefinition definition = new RouteDefinition();
                definition.setId(gatewayDefine.getServiceId());
                definition.setUri(new URI(gatewayDefine.getUri()));
                List<PredicateDefinition> predicateDefinitions = getPredicateDefinition(gatewayDefine.getPredicates());
                if (predicateDefinitions != null) {
                    definition.setPredicates(predicateDefinitions);
                }
                List<FilterDefinition> filterDefinitions = getFilterDefinition(gatewayDefine.getFilters());
                if (filterDefinitions != null) {
                    definition.setFilters(filterDefinitions);
                }
                routeDefinitionWriter.save(Mono.just(definition)).subscribe();
                context.publishEvent(new RefreshRoutesEvent(this));
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
    }

    /**
     * 获取所有的 自定义路由规则
     *
     * @param gatewayDefine
     * @return
     * @throws Exception
     */
    @Override
    public GatewayRoutesEntity save(GatewayRoutesEntity gatewayDefine) throws Exception {
        log.info("save RouteDefinition : {}", gatewayDefine);
        redisTemplate.opsForList().rightPush(GATEWAY_DEFINE_LIST_KEY, gatewayDefine);
        return gatewayDefine;
    }

    @Override
    public void deleteById(String id) throws Exception {
        List<GatewayRoutesEntity> all = findAll();
        for (GatewayRoutesEntity gatewayDefine : all) {
            if (gatewayDefine.getServiceId().equals(id)) {
                redisTemplate.opsForList().remove(GATEWAY_DEFINE_LIST_KEY, 0, gatewayDefine);
            }
        }
    }

    @Override
    public boolean existsById(String id) throws Exception {
        List<GatewayRoutesEntity> all = findAll();
        for (GatewayRoutesEntity gatewayDefine : all) {
            if (gatewayDefine.getServiceId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<PredicateDefinition> getPredicateDefinition(String predicates) {
        if (StringUtils.isNotBlank(predicates)) {
            List<PredicateDefinition> predicateDefinitionList = JSON.parseArray(predicates, PredicateDefinition.class);
            return predicateDefinitionList;
        } else {
            return null;
        }
    }

    @Override
    public List<FilterDefinition> getFilterDefinition(String filters) {
        if (StringUtils.isNotBlank(filters)) {
            List<FilterDefinition> filterDefinitionList = JSON.parseArray(filters, FilterDefinition.class);
            return filterDefinitionList;
        } else {
            return null;
        }
    }
}
