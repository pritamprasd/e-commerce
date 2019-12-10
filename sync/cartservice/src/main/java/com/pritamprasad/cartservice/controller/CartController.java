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
import java.util.UUID;

import static com.pritamprasad.cartservice.util.Constants.TOKEN;

@RestController
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/cart/{userid}")
    public ResponseEntity<Cart> getCart(@PathVariable("userid") UUID userid) {
        return ResponseEntity.ok(cartRepository.findById(userid).orElseThrow(CartNotFoundException::new));
    }

    @PostMapping("/cart")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        if(!cartRepository.findById(cart.getUserId()).isPresent()){
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

    @DeleteMapping("/cart")
    public ResponseEntity deleteCart(@PathVariable("userid") UUID userid) {
        cartRepository.deleteById(userid);
        return ResponseEntity.ok().build();
    }
}
