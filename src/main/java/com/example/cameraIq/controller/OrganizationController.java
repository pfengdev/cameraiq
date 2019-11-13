package com.example.cameraIq.controller;

import com.example.cameraIq.model.Organization;
import com.example.cameraIq.model.User;
import com.example.cameraIq.repository.OrganizationRepository;
import com.example.cameraIq.repository.UserRepository;
import com.example.cameraIq.service.OrganizationService;
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
    private OrganizationService orgService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/orgs", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Set<Organization>> getOrganizations() throws Exception {
        Set<Organization> result = orgService.getOrganizations();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/orgs/{id}/users", consumes = "application/json", produces =
                "application/json")
    @ResponseBody
    public ResponseEntity<Set<User>> getUsersForOrganization(@PathVariable long id) throws Exception  {
        Set<User> result = orgService.getUsersForOrganization(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(path = "/orgs", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization org) throws Exception  {
        Organization result = orgService.createOrganization(org);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @PutMapping(path = "/orgs/{orgId}/users/{userId}", consumes = "application/json", produces =
                "application/json")
    @ResponseBody
    public ResponseEntity<Organization> addUserToOrg(@PathVariable long orgId,
                                                     @PathVariable long userId) throws Exception {
        Organization result = orgService.addUserToOrg(orgId, userId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(path = "/orgs/{orgId}/users/{userId}", consumes = "application/json", produces =
                    "application/json")
    @ResponseBody
    public ResponseEntity<Organization> deleteUserFromOrg(@PathVariable long orgId,
                                                          @PathVariable long userId) throws Exception  {
        Organization result = orgService.deleteUserFromOrg(orgId, userId);
        return new ResponseEntity(result, HttpStatus.NO_CONTENT);
    }
}
