package com.caiflower.cloud.gateway.core.limit;

import com.caiflower.cloud.gateway.core.limit.model.Rule;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author caiflower
 * @date 2022/9/20 09:25
 */
@ConfigurationProperties("gateway.limiting")
public class LimitingConfigProperties {

    List<Rule> rules;

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Rule> getRules() {
        return rules;
    }
}
