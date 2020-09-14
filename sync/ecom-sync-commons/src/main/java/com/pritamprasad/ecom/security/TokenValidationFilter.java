package com.pritamprasad.ecom.security;

import com.netflix.discovery.EurekaClient;
import com.pritamprasad.ecom.exception.InvalidTokenException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class TokenValidationFilter implements Filter{

    private RestTemplate restTemplate;

    private EurekaClient discoveryClient;

    private String serviceName;

    public TokenValidationFilter(RestTemplate restTemplate, EurekaClient discoveryClient, String serviceName) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
        this.serviceName = serviceName;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (!req.getMethod().equals("OPTIONS")) {
            if (req.getHeader("token") == null) {
                throw new InvalidTokenException("No token provided in header");
            }
            try {
                ResponseEntity<String> reponse = restTemplate.getForEntity(
                        discoveryClient.getNextServerFromEureka(serviceName, false).getHomePageUrl() + "validate/" + req.getHeader("token"),
                        String.class);
                if (reponse.getStatusCode().is2xxSuccessful()) {
                    chain.doFilter(request, response);
                }
            } catch (RestClientException e) {
                throw new InvalidTokenException("Invalid token provided in header");
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
