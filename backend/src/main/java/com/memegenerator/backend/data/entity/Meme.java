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
public class Meme extends BaseEntity {

    public Meme()
    {
    }

    public Meme(String title, byte[] imageblob)
    {
        this.title = title;
        this.imageblob = imageblob;
    }

    public Meme(String title, byte[] imageblob, boolean activated, User user, Category category)
    {
        this.title = title;
        this.imageblob = imageblob;
        this.activated = activated;
        this.user = user;
        this.category = category;
    }

    @Column(name = "title", nullable = false)
    @NotNull
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image", nullable = false)
    private byte[] imageblob;

    @Column(name = "likes")
    private int likes;

    @Column(name = "dislikes")
    private int dislikes;

    @Column(name = "flag_points")
    private int flag_points;

    @Column(name = "memestatus")
    private String memestatus;

    @Column(name = "disabled", nullable = false)
    private boolean activated;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "categoryid", nullable = false)
    private Category category;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "meme_tags", joinColumns = { @JoinColumn(name = "memeid") }, inverseJoinColumns = {
            @JoinColumn(name = "tagid") })
            private Set<Tag> tags = new HashSet<>();
}