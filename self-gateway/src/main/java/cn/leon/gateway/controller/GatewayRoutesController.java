package cn.leon.gateway.controller;

import cn.leon.gateway.service.DynamicRouteService;
import cn.leon.gateway.service.impl.RefreshRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName GatewayRoutesController
 * @Description
 * @Author Jevon
 * @Date2020/1/10 17:17
 **/
@RestController
@RequestMapping("/gateway")
public class GatewayRoutesController {
    @Autowired
    private RefreshRouteService refreshRouteService;

    @Autowired
    private DynamicRouteService dynamicRouteService;

    @GetMapping("/refresh")
    public String refreshRoutes() {
        refreshRouteService.refreshRoutes();
        return "success";
    }

    /**
     * @param definition
     * @return
     */
    @PostMapping("/routes/add")
    public String add(@RequestBody RouteDefinition definition) {
        boolean flag = dynamicRouteService.add(definition);
        return flag ? "success" : "failer";
    }

    /**
     * @param definition
     * @return
     */
    @PostMapping("routes/update")
    public String update(@RequestBody RouteDefinition definition) {
        boolean flag = dynamicRouteService.update(definition);
        return flag ? "success" : "failer";
    }

    /**
     * @param serviceId
     * @return
     */
    @PostMapping("routes/del")
    public String update(@RequestParam("serviceId") String serviceId) {
        boolean flag = dynamicRouteService.del(serviceId);
        return flag ? "success" : "failer";
    }
}
