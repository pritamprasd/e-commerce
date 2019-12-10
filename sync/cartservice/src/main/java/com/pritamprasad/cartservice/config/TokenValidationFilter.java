package com.pritamprasad.cartservice.config;

import com.netflix.discovery.EurekaClient;
import com.pritamprasad.cartservice.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)
public class TokenValidationFilter implements Filter {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient discoveryClient;

    @Value("${authservice.name}")
    private String authService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getHeader("token") == null) {
            throw new InvalidTokenException("No token provided in header");
        }
        try {
            ResponseEntity<String> reponse = restTemplate.getForEntity(
                    discoveryClient.getNextServerFromEureka(authService, false).getHomePageUrl() + "validate/" + req.getHeader("token"),
                    String.class);
            if (reponse.getStatusCode().is2xxSuccessful()) {
                chain.doFilter(request, response);
            }
        } catch (RestClientException e) {
            throw new InvalidTokenException("Invalid token provided in header");
        }
    }
}
