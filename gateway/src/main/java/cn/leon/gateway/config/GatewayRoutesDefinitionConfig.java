package cn.leon.gateway.config;

import cn.leon.gateway.dao.GatewayRouteRespository;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @ClassName GatewayRoutesDefinitionConfig
 * @Description
 * @Author Jevon
 * @Date2020/1/9 17:28
 **/
@Configuration
public class GatewayRoutesDefinitionConfig {

    @Bean
    public RouteDefinitionLocator routeDefinitionLocator() {
        return new GatewayRouteRespository();
    }

    @Bean
    @Primary
    public RouteDefinitionWriter routeDefinitionWriter() {
        return new GatewayRouteRespository();
    }
}
