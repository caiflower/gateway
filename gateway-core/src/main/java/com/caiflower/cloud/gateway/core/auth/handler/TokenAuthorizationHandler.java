package com.caiflower.cloud.gateway.core.auth.handler;

import com.caiflower.cloud.gateway.core.auth.AuthConfigProperties;
import com.caiflower.commons.redis.CacheProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * token认证处理器
 *
 * @author caiflower
 * @date 2022/9/9 10:33
 */
@Component
@EnableConfigurationProperties(AuthConfigProperties.class)
public class TokenAuthorizationHandler implements AuthorizationHandler {

    private static final String CACHE_USER_LOGIN_KEY = "";

    private AuthConfigProperties config;

    private CacheProvider redis;

    public TokenAuthorizationHandler(AuthConfigProperties authConfigProperties, CacheProvider cacheProvider) {
        config = authConfigProperties;
        redis = cacheProvider;
    }

    public String getTokenName() {
        return config.getTokenName();
    }

    @Override
    public boolean isLoginPath(String path) {
        return config.getLoginPath().equals(path);
    }

    @Override
    public boolean isExcludePath(String path) {
        if (path == null) {
            return false;
        }
        String excludePaths = config.getExcludePaths();
        if (excludePaths != null) {
            for (String s : excludePaths.split(";")) {
                if (path.equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getLoginPath() {
        return config.getLoginPath();
    }

    @Override
    public boolean isLoginStatus(String accessToken) {
        return redis.exist(accessToken);
    }

    @Override
    public String getUserId(String accessToken) {
        return redis.get(CACHE_USER_LOGIN_KEY, String.class);
    }
}
