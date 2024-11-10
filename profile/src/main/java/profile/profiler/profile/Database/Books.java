package profile.profiler.profile.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "bookname", nullable = false)
    private String bookname;


    @CreationTimestamp
    @Column(name = "upload time")
    private Date date;



    @Column(name = "language_name", nullable = false)
    private String language_name;

    @Column(name = "synopsis", nullable = false, length = 500)
    private String synopsis;

    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @OneToMany(mappedBy = "books", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapters> chapters = new ArrayList<>();

    @OneToMany(mappedBy = "books", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reviews> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "books",  cascade = CascadeType.ALL,orphanRemoval = true)
    private ImageDB imageDB;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "sellerinventory_id", nullable = false)
    private Sellerinventory sellerinventory;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_inventory_id")
    private UserInventory userInventory;

}