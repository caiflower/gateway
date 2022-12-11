package com.caiflower.cloud.gateway.core.route.model;

import java.util.List;

/**
 * 路由
 *
 * @author caiflower
 * @date 2022/9/8 15:35
 */
public class Route {

    private String id;

    private String host;

    private String path;

    private String uri;

    private List<Filter> filters;

    private int order;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

    public String getUri() {
        return uri;
    }

    public int getOrder() {
        return order;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Route{" +
                "host='" + host + '\'' +
                ", path='" + path + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
