package cn.leon.gateway.service;

import cn.leon.gateway.model.GatewayRoutesEntity;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

import java.util.List;

/**
 * @ClassName GatewayRoutesService
 * @Description
 * @Author Jevon
 * @Date2020/1/10 15:36
 **/
public interface GatewayRoutesService {

    List<GatewayRoutesEntity> findAll() throws Exception;

    String loadRouteDefinition() throws Exception;

    GatewayRoutesEntity save(GatewayRoutesEntity gatewayDefine) throws Exception;

    void deleteById(String id) throws Exception;

    boolean existsById(String id)throws Exception;

    List<PredicateDefinition> getPredicateDefinition(String predicates) ;

    List<FilterDefinition> getFilterDefinition(String filters) ;
}
