package com.pritamprasad.authservice.controller;

import com.pritamprasad.authservice.exception.AuthServiceException;
import com.pritamprasad.authservice.exception.InvalidTokenException;
import com.pritamprasad.authservice.exception.UserNotFoundException;
import com.pritamprasad.authservice.models.User;
import com.pritamprasad.authservice.repo.TokenRepository;
import com.pritamprasad.authservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Access to these operations are based on valid token in token header.
 */
@RestController
public class UserSpecificOperations {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getItems(@PathVariable("userId") UUID id, @RequestHeader("token") String token) throws AuthServiceException {
        tokenRepository.findByTokenData(token).orElseThrow(InvalidTokenException::new);
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setPassword("");
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{userid}")
    public ResponseEntity removeItem(@PathVariable("userid") UUID id, @RequestHeader("token") String token){
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
