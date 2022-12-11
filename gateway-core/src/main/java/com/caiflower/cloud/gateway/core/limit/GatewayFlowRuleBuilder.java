package com.caiflower.cloud.gateway.core.limit;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;

/**
 * @author caiflower
 * @date 2022/9/19 20:02
 */
public class GatewayFlowRuleBuilder {

    private String routeId;
    private int count = 10;
    private int intervalSec = 1;
    private int controlBehavior = RuleConstant.CONTROL_BEHAVIOR_DEFAULT;
    private int maxQueueingTimeout = 600;
    private int parseStrategy = SentinelGatewayConstants.PARAM_PARSE_STRATEGY_CLIENT_IP;
    private int warmUpPeriodSec = 10;
    private String fieldName = "default";
    private String pattern;
    private int matchStrategy = SentinelGatewayConstants.PARAM_MATCH_STRATEGY_EXACT;

    public void setMatchStrategy(int matchStrategy) {
        this.matchStrategy = matchStrategy;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setControlBehavior(int controlBehavior) {
        this.controlBehavior = controlBehavior;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setIntervalSec(int intervalSec) {
        this.intervalSec = intervalSec;
    }

    public void setMaxQueueingTimeout(int maxQueueingTimeout) {
        this.maxQueueingTimeout = maxQueueingTimeout;
    }

    public void setParseStrategy(int parseStrategy) {
        this.parseStrategy = parseStrategy;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setWarmUpPeriodSec(int warmUpPeriodSec) {
        this.warmUpPeriodSec = warmUpPeriodSec;
    }

    public GatewayFlowRule build() {
        if (routeId == null) {
            return null;
        }
        GatewayFlowRule rule = new GatewayFlowRule(routeId);
        if (controlBehavior == RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER) {
            rule.setMaxQueueingTimeoutMs(maxQueueingTimeout);
            rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER);
        } else if (controlBehavior == RuleConstant.CONTROL_BEHAVIOR_WARM_UP) {
            rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_WARM_UP);
        } else {
            rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        }
        rule.setCount(count);
        rule.setIntervalSec(intervalSec);
        GatewayParamFlowItem gatewayParamFlowItem = new GatewayParamFlowItem();
        gatewayParamFlowItem.setParseStrategy(parseStrategy);
        if (parseStrategy != SentinelGatewayConstants.PARAM_PARSE_STRATEGY_CLIENT_IP) {
            if (fieldName != null) {
                gatewayParamFlowItem.setFieldName(fieldName);
            }
            if (pattern != null) {
                gatewayParamFlowItem.setPattern(pattern);
                // 匹配规则设置
                gatewayParamFlowItem.setMatchStrategy(matchStrategy);
            }
        }
        rule.setParamItem(gatewayParamFlowItem);
        return rule;
    }

    public void clean() {
        routeId = null;
        count = 10;
        intervalSec = 1;
        controlBehavior = RuleConstant.CONTROL_BEHAVIOR_DEFAULT;
        maxQueueingTimeout = 600;
        parseStrategy = SentinelGatewayConstants.PARAM_PARSE_STRATEGY_CLIENT_IP;
        warmUpPeriodSec = 10;
        fieldName = "default";
        pattern = null;
    }

}
