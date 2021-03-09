package com.memegenerator.backend.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Category extends BaseEntity {

    @Column(name = "title", nullable = false)
    @NotNull
    public String title;
}