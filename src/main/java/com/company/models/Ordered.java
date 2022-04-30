package com.company.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ordered")
@Data
public class Ordered {
    @EmbeddedId
    private OrderedId orderedId;

    private int price;

    @Column(name = "count_med")
    private int count;

    @ManyToOne
    @JoinColumn(name = "fk_medication", referencedColumnName = "id_medication", insertable = false, updatable = false)
    private Medication medication;
}
