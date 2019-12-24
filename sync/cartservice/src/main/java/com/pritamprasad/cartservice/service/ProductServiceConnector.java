package com.pritamprasad.cartservice.service;

import com.netflix.discovery.EurekaClient;
import com.pritamprasad.cartservice.exception.InvalidTokenException;
import com.pritamprasad.cartservice.exception.ProductNotFoundException;
import com.pritamprasad.cartservice.models.Product;
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

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceConnector {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient discoveryClient;

    @Value("${productsservice.name}")
    private String productsService;

    public Product getProduct(UUID productId, String token) {
        Product product = null;
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Product> reponse = restTemplate.exchange(
                    discoveryClient.getNextServerFromEureka(productsService, false).getHomePageUrl() + "products/" + productId,
                    HttpMethod.GET,
                    entity,
                    Product.class);
            if (reponse.getStatusCode().is2xxSuccessful()) {
                product = reponse.getBody();
            }
        } catch (RestClientException e) {
            throw new InvalidTokenException("Invalid token provided in header");
        }
        return Optional.ofNullable(product).orElseThrow(ProductNotFoundException::new);
    }
}
