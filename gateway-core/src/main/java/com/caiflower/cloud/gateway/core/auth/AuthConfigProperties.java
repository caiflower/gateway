package com.caiflower.cloud.gateway.core.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author caiflower
 * @date 2022/9/19 14:54
 */
@RefreshScope
@ConfigurationProperties(prefix = "gateway.auth")
public class AuthConfigProperties {

    private String tokenName = "token";

    private String loginPath = "127.0.0.1/login";

    private String excludePaths;

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public void setLoginPath(String loginPath) {
        this.loginPath = loginPath;
    }

    public String getTokenName() {
        return tokenName;
    }

    public String getLoginPath() {
        return loginPath;
    }

    public void setExcludePaths(String excludePaths) {
        this.excludePaths = excludePaths;
    }

    public String getExcludePaths() {
        return excludePaths;
    }
}
