package com.pritamprasad.authservice.controller;

import com.pritamprasad.authservice.exception.AuthServiceException;
import com.pritamprasad.authservice.exception.CreateCartFailedException;
import com.pritamprasad.authservice.exception.InvalidTokenException;
import com.pritamprasad.authservice.exception.UserNotFoundException;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.pritamprasad.authservice.util.HelperFunctions.generateNewToken;
import static com.pritamprasad.authservice.util.HelperFunctions.maskSecret;

/**
 * Only admin user can access these endpoint
 */
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

    /**
     * Provides all users in system.
     */
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok(userList.stream().map(HelperFunctions::maskSecret).collect(Collectors.toList()));
    }

    /**
     * Creates a new user
     * @param user @{@link User} new user object
     * @return Created @{@link User} user with id
     */
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) throws AuthServiceException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User u = userRepository.save(user);
        Token t = tokenRepository.save(new Token(u.getUserId(), generateNewToken(), System.currentTimeMillis()));
        cartServiceConnector.createCart(u.getUserId(),t.getTokenData());
        return ResponseEntity.ok(maskSecret(user));
    }

    /**
     * Updates a user object
     * @param user new @{@link User} object
     * @return updated @{@link User} object
     */
    @PutMapping("/users")
    public ResponseEntity<User> updateItem(@RequestBody User user){
        return ResponseEntity.ok(userRepository.save(user));
    }

    /**
     * Deletes a user
     * @param user @{@link User} containing id, whose details need tp be deleted
     * @return
     */
    @DeleteMapping("/users")
    public ResponseEntity removeSelfUser(@RequestBody User user) throws AuthServiceException {
        userRepository.findById(user.getUserId()).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(user.getUserId());
        return ResponseEntity.ok().build();
    }

}
