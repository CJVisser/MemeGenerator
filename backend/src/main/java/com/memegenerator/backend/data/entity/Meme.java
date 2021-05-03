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

    @Column(name = "title", nullable = false)
    @NotNull
    public String title;

    @Column(name = "description")
    public String description;

    @Column(name = "image", nullable = false)
    public byte[] imageblob;

    @Column(name = "likes")
    public int likes;

    @Column(name = "dislikes")
    public int dislikes;

    @Column(name = "flag_points")
    public int flag_points;

    @Column(name = "memestatus")
    public String memestatus;

    @Column(name = "disabled", nullable = false)
    public boolean activated;

    @ManyToOne
    @JoinColumn(name = "imageid")
    public Image image;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    public User user;

    @ManyToOne
    @JoinColumn(name = "categoryid", nullable = false)
    public Category category;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "meme_tags", joinColumns = { @JoinColumn(name = "memeid") }, inverseJoinColumns = {
            @JoinColumn(name = "tagid") })
    public Set<Tag> tags = new HashSet<>();
}