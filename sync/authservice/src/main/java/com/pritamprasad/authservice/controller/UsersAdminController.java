package com.pritamprasad.authservice.controller;

import com.pritamprasad.authservice.models.User;
import com.pritamprasad.authservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UsersAdminController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<User> addItem(@RequestBody User user){
        return ResponseEntity.ok(userRepository.save(user));
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
