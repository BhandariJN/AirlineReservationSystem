package com.airlinereservationsystem.airlinesreservationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;


    public Role(String name) {
        this.roleName = name;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users =new HashSet<User>();

}
