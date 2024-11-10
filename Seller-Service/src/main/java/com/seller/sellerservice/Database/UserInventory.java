package com.seller.sellerservice.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_inventory")
public class UserInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name="book_read")
    private int read;

    @OneToOne(mappedBy = "userInventory", cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private LocalUsers localUsers;

    @OneToMany(mappedBy = "userInventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapters> chapterses = new ArrayList<>();



    @OneToMany(mappedBy = "userInventory", orphanRemoval = true)
    private List<Reviews> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "userInventory", orphanRemoval = true)
    private List<Orders> orderses = new ArrayList<>();

    @OneToMany(mappedBy = "userInventory", orphanRemoval = true)
    private List<Books> bookses = new ArrayList<>();

}