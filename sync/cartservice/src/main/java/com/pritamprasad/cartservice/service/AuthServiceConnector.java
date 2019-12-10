package com.pritamprasad.cartservice.service;

import com.netflix.discovery.EurekaClient;
import com.pritamprasad.cartservice.exception.InvalidTokenException;
import com.pritamprasad.cartservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class AuthServiceConnector {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient discoveryClient;

    @Value("${authservice.name}")
    private String authService;

    public boolean verifyUserExist(UUID userId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<User> reponse = restTemplate.exchange(
                    discoveryClient.getNextServerFromEureka(authService, false).getHomePageUrl() + "users/" + userId,
                    HttpMethod.GET,
                    entity,
                    User.class);
            if (reponse.getStatusCode().is2xxSuccessful() && reponse.getBody().getUserId() != null) {
                return  true;
            }
        } catch (RestClientException e) {
            throw new InvalidTokenException("Invalid token provided in header");
        }
        return false;
    }
}
