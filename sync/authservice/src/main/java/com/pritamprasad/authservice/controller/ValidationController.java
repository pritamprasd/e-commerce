package com.pritamprasad.authservice.controller;

import com.pritamprasad.authservice.exception.InvalidTokenException;
import com.pritamprasad.authservice.exception.UserNotFoundException;
import com.pritamprasad.authservice.models.Token;
import com.pritamprasad.authservice.models.User;
import com.pritamprasad.authservice.repo.TokenRepository;
import com.pritamprasad.authservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.pritamprasad.authservice.util.HelperFunctions.generateNewToken;

@RestController
public class ValidationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;


    @CrossOrigin(origins = "*")
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

    @CrossOrigin(origins = "*")
    @GetMapping("/validate/{token}")
    public ResponseEntity<User> validateToken(@PathVariable("token") String token){
        Token t = tokenRepository.findByTokenData(token).orElseThrow(InvalidTokenException::new);
        User u = userRepository.findById(t.getUserId()).orElseThrow(UserNotFoundException::new);
        u.setPassword("");
        return ResponseEntity.ok(u);
    }


}
