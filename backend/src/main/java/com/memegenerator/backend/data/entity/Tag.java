package com.memegenerator.backend.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Tag extends BaseEntity {

    public Tag(String title)
    {
        this.title = title;
    }
    
    @Column(name = "title", nullable = false)
    @NotNull
    public String title;
}