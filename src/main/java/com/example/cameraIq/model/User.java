package com.example.cameraIq.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private long id;

    @Column
    @JsonProperty
    private String firstName;

    @Column
    @JsonProperty
    private String lastName;

    @Column
    @JsonProperty
    private String email;

    @Column
    @JsonProperty
    private String address;

    @Column
    @JsonProperty
    private String phone;

    @ManyToMany
    private Set<Organization> organizations;
}
