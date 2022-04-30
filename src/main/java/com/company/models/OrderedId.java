package com.company.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class OrderedId implements Serializable {
    @Column(name = "fk_order")
    private int fkOrder;
    @Column(name = "fk_medication")
    private int fkMedication;
}
