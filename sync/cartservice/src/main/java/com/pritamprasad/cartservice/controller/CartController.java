package com.pritamprasad.cartservice.controller;

import com.pritamprasad.cartservice.exception.CartNotFoundException;
import com.pritamprasad.cartservice.models.Cart;
import com.pritamprasad.cartservice.models.Product;
import com.pritamprasad.cartservice.repo.CartRepository;
import com.pritamprasad.cartservice.service.AuthServiceConnector;
import com.pritamprasad.cartservice.service.ProductServiceConnector;
import com.pritamprasad.cartservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.pritamprasad.cartservice.util.Constants.TOKEN;

@RestController
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductServiceConnector productServiceConnector;

    @CrossOrigin(origins = "*")
    @GetMapping("/cart/{userid}")
    public ResponseEntity<Cart> getCart(@PathVariable("userid") UUID userid) {
        return ResponseEntity.ok(cartRepository.findById(userid).orElseThrow(CartNotFoundException::new));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/cart/{userid}/{productid}")
    public ResponseEntity<Cart> createCart(@PathVariable("userid") UUID userid, @PathVariable("productid") UUID productid, HttpServletRequest httpServletRequest) {
        Cart c = cartRepository.findById(userid).orElseThrow(CartNotFoundException::new);
        ArrayList<UUID> productsInCart = Optional.ofNullable(c.getProductsInCart()).orElseGet(ArrayList::new);
        if(productsInCart.contains(productid)){
            //TODO: To add multiple same item in cart
        }
        Product p = productServiceConnector.getProduct(productid, httpServletRequest.getHeader("token"));
        productsInCart.add(productid);
        c.setProductsInCart(productsInCart);
        c.setCartTotal(c.getCartTotal() + p.getProductPrice());
        return ResponseEntity.ok(cartRepository.save(c));
    }

    @PostMapping("/cart")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        if (!cartRepository.findById(cart.getUserId()).isPresent()) {
            cart.setLastUpdated(System.currentTimeMillis());
            return ResponseEntity.ok(cartRepository.save(cart));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/cart")
    public ResponseEntity<Cart> addProductsToCart(@RequestBody Cart cart) {
        cartRepository.findById(cart.getUserId()).orElseThrow(CartNotFoundException::new);
        cart.setLastUpdated(System.currentTimeMillis());
        return ResponseEntity.ok(cartRepository.save(cart));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/cart/{userId}/{productId}")
    public ResponseEntity deleteCart(@PathVariable("productId") UUID productId, @PathVariable("userId") UUID userId, HttpServletRequest httpServletRequest) {
        Cart c = cartRepository.findById(userId).orElseThrow(CartNotFoundException::new);
        Product p = productServiceConnector.getProduct(productId, httpServletRequest.getHeader("token"));
        ArrayList<UUID> productsInCart = Optional.ofNullable(c.getProductsInCart()).orElseGet(ArrayList::new);
        if(productsInCart.size() != 0){
            productsInCart.remove(productId);
        }
        c.setProductsInCart(productsInCart);
        c.setCartTotal(c.getCartTotal() - p.getProductPrice());
        cartRepository.save(c);
        return ResponseEntity.ok().build();
    }

    /**
     * Clears all products from a cart
     * @param userId
     * @param httpServletRequest
     * @return
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping("/cart/{userid}")
    public ResponseEntity<Cart> deleteCart(@PathVariable("userid") UUID userId, HttpServletRequest httpServletRequest) {
        Cart c = cartRepository.findById(userId).orElseThrow(CartNotFoundException::new);
        c.setProductsInCart(new ArrayList<>());
        c.setCartTotal(0.0);
        c.setLastUpdated(System.currentTimeMillis());
        cartRepository.save(c);
        return ResponseEntity.ok(c);
    }
}
