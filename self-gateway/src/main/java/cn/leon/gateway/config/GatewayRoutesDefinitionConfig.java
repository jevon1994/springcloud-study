package cn.leon.gateway.config;

import cn.leon.gateway.service.impl.GatewayRoutesServiceImpl;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        return new GatewayRoutesServiceImpl();
    }

    @Bean
    public RouteDefinitionWriter routeDefinitionWriter() {
        return new GatewayRoutesServiceImpl();
    }
}
