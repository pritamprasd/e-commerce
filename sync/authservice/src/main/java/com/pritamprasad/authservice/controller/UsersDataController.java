package com.pritamprasad.authservice.controller;

import com.pritamprasad.authservice.advice.UserNotFoundException;
import com.pritamprasad.authservice.models.User;
import com.pritamprasad.authservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UsersDataController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getItems(@PathVariable("userId") UUID id){
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setPassword("");
        return ResponseEntity.ok(user);
    }
}
