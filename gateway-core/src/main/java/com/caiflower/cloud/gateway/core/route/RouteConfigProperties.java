package com.caiflower.cloud.gateway.core.route;

import com.caiflower.cloud.gateway.core.route.model.Route;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * 路由配置信息
 *
 * @author caiflower
 * @date 2022/9/8 14:56
 */
@RefreshScope
@ConfigurationProperties(prefix = "gateway")
public class RouteConfigProperties {

    private List<Route> routes;

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Route> getRoutes() {
        return routes;
    }
}
