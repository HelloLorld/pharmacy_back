package com.company.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "medication")
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medication")
    private int id;

    @Column(name = "name_medication")
    private String name;

    @Column(name = "sale_price")
    private int salePrice;

    @Column(name = "purchase_price")
    private int purchasePrice;

    @ManyToOne
    @JoinColumn(name = "fk_manufacturer", referencedColumnName = "id_manufacturer")
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "fk_packaging", referencedColumnName = "id_packaging")
    private Packaging packaging;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "application_medication",
            joinColumns = { @JoinColumn(name = "fk_medication") },
            inverseJoinColumns = { @JoinColumn(name = "fk_disease") }
    )
    private List<Disease> diseases;
}
