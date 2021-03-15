package com.memegenerator.backend.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Achievement extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotNull
    public String title;

    @ManyToMany(mappedBy = "achievements")
    public Set<User> users = new HashSet<>();
}