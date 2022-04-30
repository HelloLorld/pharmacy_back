package com.company.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_client", referencedColumnName = "id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "fk_saler", referencedColumnName = "id_saler")
    private Saler saler;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_order", referencedColumnName = "id_order")
    private List<Ordered> ordered;
}
