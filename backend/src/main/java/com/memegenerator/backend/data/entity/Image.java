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

    public Image(String title, byte[] imageblob, User user)
    {
        this.title = title;
        this.imageblob = imageblob;
        this.user = user;
    }

    @Column(name = "title", nullable = false)
    @NotNull
    private String title;

    @Column(name = "image", nullable = false)
    @NotNull
    private byte[] imageblob;

    @OneToMany(mappedBy = "image")
    private Set<Meme> memes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user;
}