package com.example.cameraIq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
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

    @ManyToMany(mappedBy="users", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Organization> orgs;
}
