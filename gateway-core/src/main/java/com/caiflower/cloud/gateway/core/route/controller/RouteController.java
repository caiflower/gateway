package com.caiflower.cloud.gateway.core.route.controller;

import com.caiflower.cloud.gateway.core.route.RouteConfigManager;
import com.caiflower.cloud.gateway.core.route.DynamicGatewayRoute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URISyntaxException;

/**
 * 提供路由刷新接口
 *
 * @author caiflower
 * @date 2022/9/8 22:30
 */
@RequestMapping("-")
@RestController
public class RouteController {

    @Resource
    private DynamicGatewayRoute dynamicGatewayRoute;

    @GetMapping("/reload")
    public String update() {
        try {
            dynamicGatewayRoute.refreshRoutes(RouteConfigManager.getRoutes());
        } catch (URISyntaxException e) {
            return "fail";
        }
        return "success";
    }

    @GetMapping("/count")
    public String getCount() {
        return RouteConfigManager.getRoutes().size() + "";
    }

}
