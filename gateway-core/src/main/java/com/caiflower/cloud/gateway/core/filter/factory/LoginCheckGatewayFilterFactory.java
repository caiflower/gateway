package com.caiflower.cloud.gateway.core.filter.factory;

import com.caiflower.cloud.gateway.core.filter.LoginCheckFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @author caiflower
 * @date 2022/9/18 13:14
 */
@Component
public class LoginCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
        return new LoginCheckFilter();
    }
}
