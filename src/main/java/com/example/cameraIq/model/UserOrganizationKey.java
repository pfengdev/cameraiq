package com.example.cameraIq.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
class UserOrganizationKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "organization_id")
    Long organizationId;
}
