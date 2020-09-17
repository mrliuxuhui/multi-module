package com.flyingwillow.resilience4jdemo.client;

import com.flyingwillow.resilience4jdemo.config.R4jFallbackConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "plm-project", url = "http://plm-project.longfor.sit/api/project", configuration = R4jFallbackConfig.class)
public interface PlmProjectClient {

    @GetMapping(value = "/detail")
    @CircuitBreaker(name = "plm-project", fallbackMethod = "")
    Object getPlanDetailByProjectId(@RequestParam("projectId") String projectId);
}
