package com.caiflower.cloud.gateway.core.auth.handler;

/**
 * @author caiflower
 * @date 2022/9/9 10:21
 */
public interface AuthorizationHandler {

    /**
     * 是否登录路径
     *
     * @param path 登录路径
     * @return true or false
     */
    boolean isLoginPath(String path);

    /**
     * 是否为不需要校验路径
     *
     * @param path 路径
     * @return true or false
     */
    boolean isExcludePath(String path);

    /**
     * 获取登录路径
     *
     * @return 登录路径
     */
    String getLoginPath();

    /**
     * 是否登录状态
     *
     * @param accessToken 访问token
     * @return true or false
     */
    boolean isLoginStatus(String accessToken);

    /**
     * 根据token获取用户id
     *
     * @param accessToken 访问token
     * @return userId
     */
    String getUserId(String accessToken);

}
