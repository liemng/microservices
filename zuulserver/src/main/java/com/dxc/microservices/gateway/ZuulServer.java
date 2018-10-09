package com.dxc.microservices.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.dxc.microservices.gateway.filters.OAuth2Filter;

@SpringBootApplication
@EnableZuulProxy
public class ZuulServer {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServer.class);
    }
    
    @Bean
    public OAuth2Filter simpleFilter() {
        return new OAuth2Filter();
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}