package com.caiflower.cloud.gateway.core.route;

import com.caiflower.cloud.gateway.core.route.model.Filter;
import com.caiflower.cloud.gateway.core.route.model.Route;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * 动态路由配置类
 *
 * @author caiflower
 * @date 2022/9/8 19:55
 */
@Component
public class DynamicGatewayRoute implements ApplicationEventPublisherAware {

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher applicationEventPublisher;

    private static final List<String> ROUTE_ID_LIST = new ArrayList<>();

    public void refreshRoutes(List<Route> routes) throws URISyntaxException {
        clearRoute();
        if (routes != null && routes.size() > 0) {
            for (Route route : routes) {
                if (route.getUri() != null) {
                    RouteDefinition routeDefinition = new RouteDefinition();
                    routeDefinition.setId(route.getId() == null ? System.currentTimeMillis() + "" : route.getId());
                    routeDefinition.setUri(new URI(route.getUri()));
                    routeDefinition.setPredicates(makePredicateDefinitions(route));
                    routeDefinition.setFilters(makeFilterDefinitions(route));
                    routeDefinition.setOrder(route.getOrder());
                    this.addRoute(routeDefinition);
                }
            }
        }
        publish();
    }

    private List<FilterDefinition> makeFilterDefinitions(Route route) {
        if (route == null) {
            return new ArrayList<>();
        }
        List<FilterDefinition> filterDefinitions = new ArrayList<>();
        List<Filter> filters = route.getFilters();
        if (filters != null && filters.size() > 0) {
            for (Filter filter : filters) {
                FilterDefinition filterDefinition = new FilterDefinition();
                filterDefinition.setName(filter.getName());
                List<String> values = filter.getValues();
                if(values != null && values.size() > 0) {
                    for (int i = 0; i < values.size(); i++) {
                        filterDefinition.addArg(i + "", values.get(i));
                    }
                }
                filterDefinitions.add(filterDefinition);
            }
        }
        return filterDefinitions;
    }

    private List<PredicateDefinition> makePredicateDefinitions(Route route) {
        if (route == null) {
            return new ArrayList<>();
        }
        List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
        String path = route.getPath();
        String host = route.getHost();
        if (path != null) {
            PredicateDefinition pathPredicateDefinition = new PredicateDefinition();
            pathPredicateDefinition.setName("Path");
            pathPredicateDefinition.addArg("pattern", path);
            predicateDefinitions.add(pathPredicateDefinition);
        }
        if (host != null) {
            PredicateDefinition hostPredicateDefinition = new PredicateDefinition();
            hostPredicateDefinition.setName("Host");
            hostPredicateDefinition.addArg("pattern", host);
            predicateDefinitions.add(hostPredicateDefinition);
        }
        return predicateDefinitions;
    }

    private void clearRoute() {
        for (String id : ROUTE_ID_LIST) {
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
        }
    }

    private void addRoute(RouteDefinition definition) {
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            ROUTE_ID_LIST.add(definition.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void publish() {
        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
