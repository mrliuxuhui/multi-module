package com.flyingwillow.resilience4jdemo.client;

import com.flyingwillow.resilience4jdemo.config.R4jFallbackConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "flyingwillow", url = "aaa", configuration = R4jFallbackConfig.class)
public interface ProductClient {

    @GetMapping(value = "/detail")
    @CircuitBreaker(name = "flyingwillow", fallbackMethod = "fallback")
    Object getDetailById(@RequestParam("productId") String productId);
}
