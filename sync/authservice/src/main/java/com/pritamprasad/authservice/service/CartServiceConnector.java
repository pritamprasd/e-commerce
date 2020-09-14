package com.pritamprasad.authservice.service;

import com.netflix.discovery.EurekaClient;
import com.pritamprasad.authservice.exception.CreateCartFailedException;
import com.pritamprasad.authservice.exception.UserNotFoundException;
import com.pritamprasad.authservice.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class CartServiceConnector {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient discoveryClient;

    @Value("${cartservice.name}")
    private String cartService;

    public void createCart(UUID userId, String token) throws CreateCartFailedException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        Cart cart = new Cart();
        cart.setUserId(userId);
        HttpEntity<Cart> entity = new HttpEntity<>(cart,headers);
        try {
            ResponseEntity<String> reponse = restTemplate.exchange(
                    discoveryClient.getNextServerFromEureka(cartService, false).getHomePageUrl() + "cart",
                    HttpMethod.POST,
                    entity,
                    String.class);
            if (!reponse.getStatusCode().is2xxSuccessful()) {
                throw new CreateCartFailedException(reponse.getStatusCode().toString());
            }
        } catch (RestClientException e) {
            throw new CreateCartFailedException();
        }
    }
}
