package com.example.cameraIq.controller;

import com.example.cameraIq.model.Organization;
import com.example.cameraIq.model.User;
import com.example.cameraIq.repository.OrganizationRepository;
import com.example.cameraIq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class OrganizationController {

    @Autowired
    private OrganizationRepository orgRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/orgs", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Organization>> getOrganization() {
        List<Organization> result = StreamSupport
                .stream(orgRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(path = "/orgs", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization org) {
        Organization result = orgRepository.save(org);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/orgs/{id}/users", consumes = "application/json", produces =
                "application/json")
    @ResponseBody
    public ResponseEntity<Set<User>> getUsersForOrganization(@PathVariable long id) {
        Set<User> result = userRepository.findByUserIdOrganizationId(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
