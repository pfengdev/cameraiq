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
public class Organization {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private long id;

    @Column
    @JsonProperty
    private String name;

    @Column
    @JsonProperty
    private String address;

    @Column
    @JsonProperty
    private String phone;

    @ManyToMany
    private Set<User> users;
}
