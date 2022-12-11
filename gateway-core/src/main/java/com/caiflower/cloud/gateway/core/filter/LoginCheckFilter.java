package com.caiflower.cloud.gateway.core.filter;

import com.caiflower.cloud.gateway.core.auth.handler.TokenAuthorizationHandler;
import com.caiflower.commons.response.WebResponse;
import com.caiflower.commons.utils.JacksonUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 登录校验过滤器
 *
 * @author caiflower
 * @date 2022/9/18 12:29
 */
public class LoginCheckFilter implements GatewayFilter, Ordered {

    /**
     * app用户id
     */
    private static final String HTTP_USER_ID = "app_user_id";

    private static TokenAuthorizationHandler authorizationHandler;

    public static void setAuthorizationHandler(TokenAuthorizationHandler authorizationHandler) {
        LoginCheckFilter.authorizationHandler = authorizationHandler;
    }

    @Override
    public int getOrder() {
        return -100;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        if (authorizationHandler.isLoginPath(path) || authorizationHandler.isExcludePath(path)) {
            chain.filter(exchange);
        }
        String accessToken = getAccessToken(request);
        if (accessToken == null) {
            return getWithoutLoginMono(exchange.getResponse());
        }
        if (!authorizationHandler.isLoginStatus(accessToken)) {
            return getWithoutLoginMono(exchange.getResponse());
        }
        // 向下游服务设置请求头
        exchange.getRequest().mutate().headers(httpHeaders -> httpHeaders.add(HTTP_USER_ID, authorizationHandler.getUserId(accessToken))).build();
        return chain.filter(exchange);
    }

    private Mono<Void> getWithoutLoginMono(ServerHttpResponse response) {
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        WebResponse webResponse = WebResponse.generateWebResponse();
        webResponse.setMsg("未登录");
        webResponse.setCode(HttpStatus.UNAUTHORIZED.value());
        webResponse.setKey("loginPath", authorizationHandler.getLoginPath());
        DataBuffer msg = response.bufferFactory().wrap(JacksonUtils.writeValueAsBytes(webResponse));
        return response.writeWith(Mono.just(msg));
    }

    /**
     * 获取token
     *
     * @param request ServerHttpRequest
     * @return token
     */
    private String getAccessToken(ServerHttpRequest request) {
        String tokenName = authorizationHandler.getTokenName();
        List<String> params = request.getQueryParams().get(tokenName);
        if (params != null && params.size() == 1) {
            return params.get(0);
        }
        List<HttpCookie> httpCookies = request.getCookies().get(tokenName);
        if (httpCookies != null && httpCookies.size() == 1) {
            return httpCookies.get(0).getValue();
        }
        List<String> headers = request.getHeaders().get(tokenName);
        if (headers != null && headers.size() == 1) {
            return headers.get(0);
        }
        return null;
    }
}
