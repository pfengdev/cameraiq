package com.example.cameraIq.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
class UserOrganization {

    @EmbeddedId
    UserOrganizationKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("organization_id")
    @JoinColumn(name = "organization_id")
    Organization organization;
}
