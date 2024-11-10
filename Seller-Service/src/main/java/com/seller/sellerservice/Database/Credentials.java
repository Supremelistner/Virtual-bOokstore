package com.seller.sellerservice.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "credentials")
public class Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "details", nullable = false)
    private String details;

    @OneToOne(cascade = CascadeType.DETACH, optional = false,orphanRemoval = true)
    @JoinColumn(name = "local_users_id")
    private LocalUsers localUsers;

}