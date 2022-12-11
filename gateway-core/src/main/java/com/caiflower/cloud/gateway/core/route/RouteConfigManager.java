package com.caiflower.cloud.gateway.core.route;

import com.caiflower.cloud.gateway.core.route.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 路由信息管理
 *
 * @author caiflower
 * @date 2022/9/8 15:33
 */
@Configuration
@EnableConfigurationProperties(RouteConfigProperties.class)
public class RouteConfigManager {

    private static RouteConfigProperties routeConfigProperties;

    @Autowired
    public void setRoutesProperties(RouteConfigProperties routesProperties) {
        routeConfigProperties = routesProperties;
    }

    public static List<Route> getRoutes() {
        return routeConfigProperties.getRoutes();
    }

}
