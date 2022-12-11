package com.caiflower.cloud.gateway.core.limit.model;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;

/**
 * @author caiflower
 * @date 2022/9/20 09:27
 */
public class Rule {

    private String routeId;
    private int count = 10;
    private int intervalSec = 1;
    private int controlBehavior = RuleConstant.CONTROL_BEHAVIOR_DEFAULT;
    private int maxQueueingTimeout = 600;
    private int parseStrategy = SentinelGatewayConstants.PARAM_PARSE_STRATEGY_CLIENT_IP;
    private int warmUpPeriodSec = 10;
    private String fieldName = "default";
    private String pattern;
    private int matchStrategy;

    public void setMatchStrategy(int matchStrategy) {
        this.matchStrategy = matchStrategy;
    }

    public int getMatchStrategy() {
        return matchStrategy;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setWarmUpPeriodSec(int warmUpPeriodSec) {
        this.warmUpPeriodSec = warmUpPeriodSec;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setParseStrategy(int parseStrategy) {
        this.parseStrategy = parseStrategy;
    }

    public void setMaxQueueingTimeout(int maxQueueingTimeout) {
        this.maxQueueingTimeout = maxQueueingTimeout;
    }

    public void setIntervalSec(int intervalSec) {
        this.intervalSec = intervalSec;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setControlBehavior(int controlBehavior) {
        this.controlBehavior = controlBehavior;
    }

    public int getControlBehavior() {
        return controlBehavior;
    }

    public int getCount() {
        return count;
    }

    public int getIntervalSec() {
        return intervalSec;
    }

    public int getMaxQueueingTimeout() {
        return maxQueueingTimeout;
    }

    public int getParseStrategy() {
        return parseStrategy;
    }

    public int getWarmUpPeriodSec() {
        return warmUpPeriodSec;
    }

    public String getFieldName() {
        return fieldName;
    }
    public String getPattern() {
        return pattern;
    }

    public String getRouteId() {
        return routeId;
    }
}
