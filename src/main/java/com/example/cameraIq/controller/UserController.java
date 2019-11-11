package com.example.cameraIq.controller;

import com.example.cameraIq.model.User;
import com.example.cameraIq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/users", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<User>> getUsers() {
         List<User> result = StreamSupport
                 .stream(userRepository.findAll().spliterator(), false)
                 .collect(Collectors.toList());
         return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User result = userRepository.save(user);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }
}
