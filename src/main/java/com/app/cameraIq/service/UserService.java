package com.app.cameraIq.service;

import com.app.cameraIq.model.Organization;
import com.app.cameraIq.model.User;
import com.app.cameraIq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Gets all the users
     * @return the set of all users
     */
    public Set<User> getUsers() {
        Set<User> result = StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
        return result;
    }

    /**
     * Gets all the orgs for the existing user
     * @param id the id of the existing user
     * @return the set of orgs for the user
     * @throws EntityNotFoundException if the user does not exist
     */
    public Set<Organization> getOrganizationsForUser(@PathVariable long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            Set<Organization> result = user.get().getOrgs();
            return result;
        }
        throw new EntityNotFoundException();
    }

    /**
     * Creates new user
     * @param user the request body of the user to be created
     * @return the newly created user
     * @throws NoSuchFieldException if required fields are null
     * @throws EntityExistsException if a user with the same name already exists
     */
    public User createUser(@RequestBody User user) throws EntityExistsException, NoSuchFieldException {
        if (user.getFirstName() == null) {
            throw new NoSuchFieldException();
        }
        if (user.getLastName() == null) {
            throw new NoSuchFieldException();
        }
        if (user.getEmail() == null) {
            throw new NoSuchFieldException();
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }
        User result = userRepository.save(user);
        return result;
    }
}
