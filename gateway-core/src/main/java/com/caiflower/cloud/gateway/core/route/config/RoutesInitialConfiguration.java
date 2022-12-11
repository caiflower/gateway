package com.caiflower.cloud.gateway.core.route.config;

import com.caiflower.cloud.gateway.core.route.RouteConfigProperties;
import com.caiflower.cloud.gateway.core.route.DynamicGatewayRoute;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.URISyntaxException;

/**
 * gateway初始化路由配置
 *
 * @author caiflower
 * @date 2022/9/8 22:39
 */
@Configuration
@EnableConfigurationProperties(RouteConfigProperties.class)
public class RoutesInitialConfiguration {

    @Resource
    private DynamicGatewayRoute dynamicGatewayRoute;

    @Resource
    private RouteConfigProperties routeConfigProperties;

    @PostConstruct
    public void init() throws URISyntaxException {
        dynamicGatewayRoute.refreshRoutes(routeConfigProperties.getRoutes());
    }
}
