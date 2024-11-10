package com.seller.sellerservice.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "about", nullable = false)
    private String about;


    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Books> books = new ArrayList<>();

}
