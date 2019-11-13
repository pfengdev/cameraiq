package com.example.cameraIq.controller;

import com.example.cameraIq.model.Organization;
import com.example.cameraIq.model.User;
import com.example.cameraIq.repository.OrganizationRepository;
import com.example.cameraIq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;
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
    public ResponseEntity<Set<Organization>> getOrganizations() {
        Set<Organization> result = StreamSupport
                .stream(orgRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/orgs/{id}/users", consumes = "application/json", produces =
                "application/json")
    @ResponseBody
    public ResponseEntity<Set<User>> getUsersForOrganization(@PathVariable long id) {
        Optional<Organization> organization = orgRepository.findById(id);
        if (organization.isPresent()) {
            return new ResponseEntity(organization.get().getUsers(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/orgs", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization org) {
        Organization result = orgRepository.save(org);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @PutMapping(path = "/orgs/{orgId}/users/{userId}", consumes = "application/json", produces =
                "application/json")
    @ResponseBody
    public ResponseEntity<Organization> addUserToOrg(@PathVariable long orgId,
                                                     @PathVariable long userId) {
        Optional<Organization> organization = orgRepository.findById(orgId);
        if (organization.isPresent()) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                organization.get().getUsers().add(user.get());
                orgRepository.save(organization.get());
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/orgs/{orgId}/users/{userId}", consumes = "application/json", produces =
                    "application/json")
    @ResponseBody
    public ResponseEntity<Organization> deleteUserFromOrg(@PathVariable long orgId,
                                                          @PathVariable long userId) {
        Optional<Organization> organization = orgRepository.findById(orgId);
        if (organization.isPresent()) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                organization.get().getUsers().remove(user.get());
                orgRepository.save(organization.get());
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
