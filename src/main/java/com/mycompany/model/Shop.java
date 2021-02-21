package com.mycompany.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "shop")
@Data
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String district;
    @NotNull
    private Double commission;

}
