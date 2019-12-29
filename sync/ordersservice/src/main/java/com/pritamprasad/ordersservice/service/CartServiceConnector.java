package com.pritamprasad.ordersservice.service;

import com.netflix.discovery.EurekaClient;
import com.pritamprasad.ordersservice.exception.CartServiceConnectionException;
import com.pritamprasad.ordersservice.exception.InvalidTokenException;
import com.pritamprasad.ordersservice.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceConnector {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient discoveryClient;

    @Value("${cartservice.name}")
    private String cartService;

    public Cart clearCart(UUID userId, String token) {
        Cart c = null;
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Cart> reponse = restTemplate.exchange(
                    discoveryClient.getNextServerFromEureka(cartService, false).getHomePageUrl() + "cart/" + userId,
                    HttpMethod.DELETE,
                    entity,
                    Cart.class);
            if (!reponse.getStatusCode().is2xxSuccessful()) {
                throw new CartServiceConnectionException();
            }
            c = reponse.getBody();
        } catch (RestClientException e) {
            throw new InvalidTokenException("Invalid token provided in header");
        }
        return c;
    }
}
