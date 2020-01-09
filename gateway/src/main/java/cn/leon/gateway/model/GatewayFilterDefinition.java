package cn.leon.gateway.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName GatewayFilterDefinition
 * @Description
 * @Author Jevon
 * @Date2020/1/9 12:02
 **/
@Data
public class GatewayFilterDefinition {
    /**
     * Filter Name
     */
    private String name;
    /**
     * 对应的路由规则
     */
    private Map<String, String> args = new LinkedHashMap();
}
