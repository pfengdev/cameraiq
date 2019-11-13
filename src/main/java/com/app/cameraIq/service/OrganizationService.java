package com.app.cameraIq.service;

import com.app.cameraIq.model.Organization;
import com.app.cameraIq.model.User;
import com.app.cameraIq.repository.OrganizationRepository;
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
public class OrganizationService {

    @Autowired
    private OrganizationRepository orgRepository;

    @Autowired
    private UserRepository userRepository;

    public Set<Organization> getOrganizations() {
        Set<Organization> result = StreamSupport
                .stream(orgRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
        return result;
    }

    public Set<User> getUsersForOrganization(@PathVariable long id) throws EntityNotFoundException {
        Optional<Organization> organization = orgRepository.findById(id);
        if (organization.isPresent()) {
            return organization.get().getUsers();
        }
        throw new EntityNotFoundException();
    }

    public Organization createOrganization(@RequestBody Organization org) throws NoSuchFieldException, EntityExistsException {
        if (org.getName() == null) {
            throw new NoSuchFieldException();
        }
        if (orgRepository.findByName(org.getName()) != null) {
            throw new EntityExistsException();
        }
        Organization result = orgRepository.save(org);
        return result;
    }

    public Organization addUserToOrg(@PathVariable long orgId,
                                     @PathVariable long userId) throws EntityNotFoundException {
        Optional<Organization> organization = orgRepository.findById(orgId);
        if (organization.isPresent()) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                organization.get().getUsers().add(user.get());
                return orgRepository.save(organization.get());
            }
            throw new EntityNotFoundException();
        }
        throw new EntityNotFoundException();
    }

    public Organization deleteUserFromOrg(@PathVariable long orgId,
                                          @PathVariable long userId) throws EntityNotFoundException {
        Optional<Organization> organization = orgRepository.findById(orgId);
        if (organization.isPresent()) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                organization.get().getUsers().remove(user.get());
                return orgRepository.save(organization.get());
            }
            throw new EntityNotFoundException();
        }
        throw new EntityNotFoundException();
    }
}
