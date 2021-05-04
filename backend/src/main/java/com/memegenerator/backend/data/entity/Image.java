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
public class Image extends BaseEntity {

    @OneToMany(mappedBy = "image")
    private Set<Meme> memes = new HashSet<>();

    @Column(name = "title", nullable = false)
    @NotNull
    public String title;

    @Column(name = "image", nullable = false)
    @NotNull
    public byte[] imageblob;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    public User user;
}