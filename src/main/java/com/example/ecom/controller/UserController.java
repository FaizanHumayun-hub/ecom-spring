package com.example.ecom.controller;

import com.example.ecom.dtos.user.LoginDto;
import com.example.ecom.dtos.user.UpdateUserRequest;
import com.example.ecom.dtos.user.UserRequest;
import com.example.ecom.entity.Role;
import com.example.ecom.service.CustomUserDetailsService;
import com.example.ecom.service.UserService;
import com.example.ecom.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;


import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService customUserDetailsService;


    @GetMapping("/alluser")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUser() {
        return new ResponseEntity<>( userService.getAllUser(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody @Valid UserRequest user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable UUID userId) {
            return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        return new ResponseEntity<>( userService.deleteUser(userId), HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateUserRequest user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginDto user){
//        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
            Set<String> role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            //String jwt = jwtUtil.generateToken(userDetails.getUsername(), role);
            String jwt = jwtUtil.generateToken(userDetails.getUsername(), role);
            return ResponseEntity.ok(jwt);
//        }catch (Exception e){
//            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
//        }
    }

}
