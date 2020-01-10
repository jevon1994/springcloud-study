package cn.leon.gateway.controller;

import cn.leon.gateway.service.DynamicRouteServiceImpl;
import cn.leon.gateway.service.RefreshRouteService;
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
public class GatewayRoutesController {
    @Autowired
    private RefreshRouteService refreshRouteService;

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    @GetMapping("/refreshRoutes")
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
    @RequestMapping(value = "routes/update", method = RequestMethod.POST)
    public String update(@RequestBody RouteDefinition definition) {
        boolean flag = dynamicRouteService.add(definition);
        return flag ? "success" : "failer";
    }

    /**
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "routes/del", method = RequestMethod.POST)
    public String update(@RequestParam("serviceId") String serviceId) {
        boolean flag = dynamicRouteService.del(serviceId);
        return flag ? "success" : "failer";
    }
}
