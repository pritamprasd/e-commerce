package com.pritamprasad.authservice.controller;

import com.pritamprasad.authservice.advice.InvalidTokenException;
import com.pritamprasad.authservice.advice.UserNotFoundException;
import com.pritamprasad.authservice.models.Token;
import com.pritamprasad.authservice.models.User;
import com.pritamprasad.authservice.repo.TokenRepository;
import com.pritamprasad.authservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ValidationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/token")
    public String getToken(@RequestBody User user) {
        String token = "";
        User user1 = userRepository.findByUserName(user.getUserName()).orElseThrow(UserNotFoundException::new);
        if (user.getPassword().equals(user1.getPassword())) {
            Token t = tokenRepository.save(new Token(user1.getUserId(), generateNewToken(), System.currentTimeMillis()));
            token = t.getTokenData();
        }
        return token;
    }

    private String generateNewToken() {
        return UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "");
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<User> validateToken(@PathVariable("token") String token){
        Token t = tokenRepository.findByTokenData(token).orElseThrow(InvalidTokenException::new);
        User u = userRepository.findById(t.getUserId()).orElseThrow(UserNotFoundException::new);
        u.setPassword("");
        return ResponseEntity.ok(u);
    }


}
