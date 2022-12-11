package com.caiflower.cloud.gateway.core.auth.config;

import com.caiflower.cloud.gateway.core.auth.handler.TokenAuthorizationHandler;
import com.caiflower.cloud.gateway.core.filter.LoginCheckFilter;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author caiflower
 * @date 2022/9/19 14:35
 */
@Configuration
public class AuthHandlerConfiguration {

    @Resource
    private TokenAuthorizationHandler tokenAuthorizationHandler;

    @PostConstruct
    private void init() {
        LoginCheckFilter.setAuthorizationHandler(tokenAuthorizationHandler);
    }

}
