package com.company.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "saler")
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Saler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_saler")
    private int id;

    @Column(name = "fio")
    private String fullName;

    private int salary;

    @Column(name = "started_work")
    private Date startedWork;

    private String post;

    private Date birthday;
}
