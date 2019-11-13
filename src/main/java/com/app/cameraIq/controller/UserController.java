package com.app.cameraIq.controller;

import com.app.cameraIq.model.Organization;
import com.app.cameraIq.model.User;
import com.app.cameraIq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Set<User>> getUsers() {
         Set<User> result = userService.getUsers();
         return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}/orgs", consumes = "application/json", produces =
            "application/json")
    @ResponseBody
    public ResponseEntity<Set<User>> getOrganizationsForUser(@PathVariable long id) throws Exception {
        Set<Organization> result = userService.getOrganizationsForUser(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(path = "/users/registration", consumes = "application/json", produces =
                 "application/json")
    @ResponseBody
    public ResponseEntity<User> createUser(@RequestBody User user) throws Exception {
        User result = userService.createUser(user);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }
}
