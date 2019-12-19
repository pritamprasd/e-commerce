package com.pritamprasad.cartservice.controller;

import com.pritamprasad.cartservice.exception.CartNotFoundException;
import com.pritamprasad.cartservice.models.Cart;
import com.pritamprasad.cartservice.repo.CartRepository;
import com.pritamprasad.cartservice.service.AuthServiceConnector;
import com.pritamprasad.cartservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static com.pritamprasad.cartservice.util.Constants.TOKEN;

@RestController
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @CrossOrigin(origins = "*")
    @GetMapping("/cart/{userid}")
    public ResponseEntity<Cart> getCart(@PathVariable("userid") UUID userid) {
        return ResponseEntity.ok(cartRepository.findById(userid).orElseThrow(CartNotFoundException::new));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/cart/{userid}/{productid}")
    public ResponseEntity<Cart> createCart(@PathVariable("userid") UUID userid, @PathVariable("productid") UUID productid) {
        Cart c = cartRepository.findById(userid).orElseThrow(CartNotFoundException::new);
        ArrayList<UUID> productsInCart = Optional.ofNullable(c.getProductsInCart()).orElseGet(ArrayList::new);
        if(productsInCart.contains(productid)){
            //TODO: To add multiple same item in cart
        }
        productsInCart.add(productid);
        c.setProductsInCart(productsInCart);
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
    public ResponseEntity deleteCart(@PathVariable("productId") UUID productId, @PathVariable("userId") UUID userId) {
        Cart c = cartRepository.findById(userId).orElseThrow(CartNotFoundException::new);
        ArrayList<UUID> productsInCart = Optional.ofNullable(c.getProductsInCart()).orElseGet(ArrayList::new);
        if(productsInCart.size() != 0){
            productsInCart.remove(productId);
        }
        c.setProductsInCart(productsInCart);
        cartRepository.save(c);
        return ResponseEntity.ok().build();
    }
}
