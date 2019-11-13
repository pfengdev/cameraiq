package com.app.cameraIq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(unique=true)
    @NotNull
    private String name;

    @Column
    private String address;

    @Column
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER,  cascade= CascadeType.MERGE)
    @JoinTable(
            name = "organization_user",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<User> users;

    public boolean equals(Organization org) {
        return org.getName().equals(this.getName()) &&
                org.getAddress().equals(this.getAddress()) &&
                org.getPhone().equals(this.getPhone());
    }
}