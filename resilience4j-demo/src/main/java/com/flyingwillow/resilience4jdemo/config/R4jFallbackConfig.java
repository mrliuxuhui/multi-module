package com.flyingwillow.resilience4jdemo.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.feign.FeignDecorator;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class R4jFallbackConfig {
    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Bean
    @Scope("prototype")
    public FeignClientBuilder.Builder feignBuilder(){
        circuitBreakerRegistry.getAllCircuitBreakers().forEach(circuitBreaker -> circuitBreaker.getName());
        FeignDecorator
    }
}
