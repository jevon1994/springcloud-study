package cn.leon.gateway.service;

import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * @ClassName DynamicRouteServiceImpl
 * @Description
 * @Author Jevon
 * @Date2020/1/10 15:28
 **/
public interface DynamicRouteService {
    /**
     * 添加路由实体类
     *
     * @param definition
     * @return
     */
    boolean add(RouteDefinition definition);

    /**
     * @param definition 路由实体类
     * @return
     */
    boolean update(RouteDefinition definition);

    /**
     * serviceId
     *
     * @param id
     * @return
     */
    boolean del(String id);
}
