package com.pritamprasad.productservice.config;

import com.netflix.discovery.EurekaClient;
import com.pritamprasad.ecom.security.TokenValidationFilter;
import com.pritamprasad.productservice.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
public class TokenValidationFilterBean {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient discoveryClient;

    @Value("${authservice.name}")
    private String authService;

    @Bean
    @Order(1)
    Filter tokenValidationFilter(){
        return new TokenValidationFilter(restTemplate, discoveryClient, authService);
    }

}
