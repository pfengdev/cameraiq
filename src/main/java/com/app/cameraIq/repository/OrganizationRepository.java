package com.app.cameraIq.repository;

import com.app.cameraIq.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    Organization findByName(String name);
}
