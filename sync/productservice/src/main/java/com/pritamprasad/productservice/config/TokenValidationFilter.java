package com.pritamprasad.productservice.config;

import com.pritamprasad.productservice.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getHeader("token") == null) {
            throw new InvalidTokenException("No token provided in header");
        }
        try {
            ResponseEntity<String> reponse = restTemplate.getForEntity("http://localhost:8092/validate/" + req.getHeader("token"), String.class);
            if (reponse.getStatusCode().is2xxSuccessful()) {
                chain.doFilter(request, response);
            }
        } catch (RestClientException e) {
            throw new InvalidTokenException("Invalid token provided in header");
        }
    }
}
