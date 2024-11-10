package com.seller.sellerservice.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_inventory_id")
    private UserInventory userInventory;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "books_id", nullable = false)
    private Books books;

}