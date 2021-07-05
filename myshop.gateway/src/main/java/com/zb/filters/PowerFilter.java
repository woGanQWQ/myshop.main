package com.zb.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class PowerFilter implements GlobalFilter, Ordered {



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath().toString();
        System.out.println(path);
        if(path.startsWith("/api/auth/login")){
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst("token");
        if(token==null || "".equals(token)){
            token=request.getQueryParams().getFirst("token");
        }
        if(token==null || "".equals(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //将令牌装到头文件中,发给具体使用用户验证的微服务
        request.mutate().header("Authorization","bearer "+token);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
