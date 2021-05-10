package com.memegenerator.backend.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Achievement extends BaseEntity {

    public Achievement(){
        
    }

    public Achievement(String title)
    {
        this.title = title;
    }

    @Column(name = "title", nullable = false)
    @NotNull
    private String title;
    @Column(name = "description", nullable = false)
    @NotNull
    public String description;
}