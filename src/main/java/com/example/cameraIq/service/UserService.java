package com.example.cameraIq.service;

import com.example.cameraIq.model.Organization;
import com.example.cameraIq.model.User;
import com.example.cameraIq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Set<User> getUsers() {
        Set<User> result = StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
        return result;
    }

    public Set<Organization> getOrganizationsForUser(@PathVariable long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            Set<Organization> result = user.get().getOrgs();
            return result;
        }
        throw new Exception();
    }

    public User createUser(@RequestBody User user) throws Exception {
        if (user.getFirstName() == null) {
            throw new Exception();
        }
        if (user.getLastName() == null) {
            throw new Exception();
        }
        if (user.getEmail() == null) {
            throw new Exception();
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception();
        }
        User result = userRepository.save(user);
        return result;
    }
}
