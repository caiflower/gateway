package com.caiflower.cloud.gateway.core.limit.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.caiflower.cloud.gateway.core.limit.GatewayFlowRuleBuilder;
import com.caiflower.cloud.gateway.core.limit.LimitingConfigProperties;
import com.caiflower.cloud.gateway.core.limit.model.Rule;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author caiflower
 * @date 2022/9/19 16:46
 */
@Configuration
@EnableConfigurationProperties(LimitingConfigProperties.class)
public class SentinelGatewayConfiguration {
    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;
    private final LimitingConfigProperties limitingConfigProperties;

    public SentinelGatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                        ServerCodecConfigurer serverCodecConfigurer, LimitingConfigProperties limitingConfigProperties) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
        this.limitingConfigProperties = limitingConfigProperties;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void doInit() {
        initGatewayRules();
    }

    private void initGatewayRules() {
        GatewayFlowRuleBuilder builder = new GatewayFlowRuleBuilder();
        List<Rule> rules = limitingConfigProperties.getRules();
        if (rules != null && rules.size() > 0) {
            Set<GatewayFlowRule> gatewayRules = new HashSet<>();
            for (Rule rule : rules) {
                builder.clean();
                builder.setRouteId(rule.getRouteId());
                builder.setCount(rule.getCount());
                builder.setIntervalSec(rule.getIntervalSec());
                builder.setControlBehavior(rule.getControlBehavior());
                builder.setMaxQueueingTimeout(rule.getMaxQueueingTimeout());
                builder.setWarmUpPeriodSec(rule.getWarmUpPeriodSec());
                builder.setParseStrategy(rule.getParseStrategy());
                builder.setFieldName(rule.getFieldName());
                builder.setPattern(rule.getPattern());
                gatewayRules.add(builder.build());
            }
            GatewayRuleManager.loadRules(gatewayRules);
        }
    }

}
