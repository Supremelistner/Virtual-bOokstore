package com.seller.sellerservice.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "chapter")
public class Chapters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "price")
    private Float price;

    @Column(name="filepath",length = 1000,nullable = false)
    private String filePath;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "books_id", nullable = false, unique = true)
    private Books books;

    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "sellerinventory_id", nullable = false)
    private Sellerinventory sellerinventory;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_inventory_id", unique = true)
    private UserInventory userInventory;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Chapters chapters = (Chapters) o;
        return getId() != null && Objects.equals(getId(), chapters.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}