package com.seller.sellerservice.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="orders")
public class Orders {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="order_id")
    private int id;
    @Column(name="invoice")
    private String invoice;
    @CreationTimestamp
    @Column(name="Date")
    private Date date;
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "chapters_id", nullable = false)
    private Chapters chapters;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sellerinventory_id")
    private Sellerinventory sellerinventory;
    @ManyToOne
    @JoinColumn(name = "user_inventory_id")
    private UserInventory userInventory;

    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    @Column(name = "price")
    private Float price;

}
