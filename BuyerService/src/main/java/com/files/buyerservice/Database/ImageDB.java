package com.files.buyerservice.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "image_db")
public class ImageDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "imagename", length = 1000)
    private String imagename;

    @Column(name = "imagetype")
    private String imagetype;

    @Column(name = "imagepath", length = 1000)
    private String imagepath;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "books_id")
    private Books books;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "local_users_id")
    private LocalUsers localUsers;

}