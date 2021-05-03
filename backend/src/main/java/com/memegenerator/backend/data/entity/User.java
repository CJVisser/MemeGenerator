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
    public String username;

    @Column(name = "password", nullable = false)
    @NotNull(message = "No password given")
    public String password;

    @Column(name = "points")
    public int points;

    @Column(name = "email", nullable = false)
    @NotNull(message = "No email given")
    public String email;

    @Column(name = "token")
    public String token;

    @Column(name = "confirmation_token")
    public int confirmationToken;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    public Role role;

    @Column(name = "activated", nullable = false)
    @NotNull(message = "No activated give")
    public boolean activated;

    @Column(name = "banned")
    public boolean banned;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "user_achievements", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = {
            @JoinColumn(name = "achievementid") })
    private Set<Achievement> achievements = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Meme> memes = new HashSet<>();
}