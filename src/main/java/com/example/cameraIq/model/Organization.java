package com.example.cameraIq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class Organization {

    @Id
    @GeneratedValue
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

    @ManyToMany(fetch = FetchType.EAGER,  cascade= CascadeType.MERGE)
    @JoinTable(
            name = "organization_user",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<User> users;
}
