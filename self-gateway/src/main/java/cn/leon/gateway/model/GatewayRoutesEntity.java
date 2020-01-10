package cn.leon.gateway.model;

import lombok.Data;

/**
 * @ClassName GatewayRoutesEntity
 * @Description
 * @Author Jevon
 * @Date2020/1/10 15:20
 **/
@Data
public class GatewayRoutesEntity {
    private Long id;

    private String serviceId;

    private String uri;

    private String predicates;

    private String filters;
}
