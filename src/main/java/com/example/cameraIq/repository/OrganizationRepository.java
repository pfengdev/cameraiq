package com.example.cameraIq.repository;

import com.example.cameraIq.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    Organization findByName(String name);
}
