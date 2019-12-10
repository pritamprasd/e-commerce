package com.pritamprasad.authservice.controller;

import com.pritamprasad.authservice.models.Cart;
import com.pritamprasad.authservice.models.Token;
import com.pritamprasad.authservice.models.User;
import com.pritamprasad.authservice.repo.TokenRepository;
import com.pritamprasad.authservice.repo.UserRepository;
import com.pritamprasad.authservice.service.CartServiceConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static com.pritamprasad.authservice.util.HelperFunctions.generateNewToken;

@RestController
public class UsersAdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CartServiceConnector cartServiceConnector;

    @PostMapping("/users")
    public ResponseEntity<User> addItem(@RequestBody User user){
        User u = userRepository.save(user);
        Token t = tokenRepository.save(new Token(u.getUserId(), generateNewToken(), System.currentTimeMillis()));
        cartServiceConnector.createCart(u.getUserId(),t.getTokenData());
        return ResponseEntity.ok(u);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateItem(@RequestBody User user){
        return ResponseEntity.ok(userRepository.save(user));
    }

    @DeleteMapping("/users/{userid}")
    public ResponseEntity removeItem(@PathVariable("userid") UUID id){
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
