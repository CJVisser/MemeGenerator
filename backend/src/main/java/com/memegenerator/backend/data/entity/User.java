package com.memegenerator.backend.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.memegenerator.backend.security.Role;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User extends BaseEntity {

    public User(String username, String password, String email, boolean activated)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.activated = activated;
    }

    public User(String username, String password, String email, Role role, boolean activated)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.activated = activated;
    }

    @Column(name = "username", nullable = false)
    @NotNull(message = "No username given")
    private String username;

    @Column(name = "password", nullable = false)
    @NotNull(message = "No password given")
    private String password;

    @Column(name = "points")
    private int points;

    @Column(name = "email", nullable = false)
    @NotNull(message = "No email given")
    private String email;

    @Column(name = "token")
    private String token;

    @Column(name = "confirmation_token")
    private int confirmationToken;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "activated", nullable = false)
    @NotNull(message = "No activated give")
    private boolean activated;

    @Column(name = "banned")
    private boolean banned;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "user_achievements", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = {
            @JoinColumn(name = "achievementid") })
    private Set<Achievement> achievements = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Meme> memes = new HashSet<>();
}