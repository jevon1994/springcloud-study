package cn.leon.gateway.domain.dto;

import lombok.Data;

/**
 * @ClassName GatewayRouteDefinition
 * @Description
 * @Author Jevon
 * @Date2020/1/9 12:01
 **/
@Data
public class GatewayRouteDefinition{
    private String serviceId;

    private String predicates;

    private String filters;

    private String uri;

    private String id;

    private int order = 0;

}
