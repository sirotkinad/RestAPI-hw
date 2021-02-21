package com.mycompany.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private String storage;
    @NotNull
    private Integer count;

}


