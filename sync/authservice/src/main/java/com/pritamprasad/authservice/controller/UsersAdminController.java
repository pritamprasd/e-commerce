package com.pritamprasad.authservice.controller;

import com.pritamprasad.authservice.models.Token;
import com.pritamprasad.authservice.models.User;
import com.pritamprasad.authservice.repo.TokenRepository;
import com.pritamprasad.authservice.repo.UserRepository;
import com.pritamprasad.authservice.service.CartServiceConnector;
import com.pritamprasad.authservice.util.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.pritamprasad.authservice.util.HelperFunctions.generateNewToken;
import static com.pritamprasad.authservice.util.HelperFunctions.maskSecret;

@RestController
public class UsersAdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CartServiceConnector cartServiceConnector;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok(userList.stream().map(HelperFunctions::maskSecret).collect(Collectors.toList()));
    }

    @PostMapping("/users")
    public ResponseEntity<User> addItem(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User u = userRepository.save(user);
        Token t = tokenRepository.save(new Token(u.getUserId(), generateNewToken(), System.currentTimeMillis()));
        cartServiceConnector.createCart(u.getUserId(),t.getTokenData());
        return ResponseEntity.ok(maskSecret(user));
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
