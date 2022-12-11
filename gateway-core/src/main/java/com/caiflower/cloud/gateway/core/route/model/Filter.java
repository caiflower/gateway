package com.caiflower.cloud.gateway.core.route.model;

import java.util.List;

/**
 * @author caiflower
 * @date 2022/9/9 14:26
 */
public class Filter {

    /**
     * 过滤类型名 https://blog.csdn.net/weixin_42073629/article/details/106890840
     */
    private String name;

    /**
     * 值
     */
    private List<String> values;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }
}
