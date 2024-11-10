package profile.profiler.profile.Database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sellerinventory")
public class Sellerinventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;


    @Column(name="buyers")
    private int buyers;

    @OneToOne(mappedBy = "sellerinventory", cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private LocalUsers localUsers;

    @OneToMany(mappedBy = "sellerinventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapters> chapterses = new ArrayList<>();

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "orders_order_id")
    private Orders orders;

    @OneToMany(mappedBy = "sellerinventory", orphanRemoval = true)
    private List<Books> bookses = new ArrayList<>();

}