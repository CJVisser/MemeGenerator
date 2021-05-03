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

    public Achievement(String title)
    {
        this.title = title;
    }

    @Column(name = "title", nullable = false)
    @NotNull
    public String title;

    @ManyToMany(mappedBy = "achievements")
    private Set<User> users = new HashSet<>();
}