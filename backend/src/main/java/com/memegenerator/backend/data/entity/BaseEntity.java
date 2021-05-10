package com.memegenerator.backend.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "createdat", unique = false, nullable = true)
    @CreatedDate
    @CreationTimestamp
    private Timestamp createdat;
}
