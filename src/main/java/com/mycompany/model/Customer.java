package com.mycompany.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customer")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String surname;
    @NotNull
    private String district;
    @NotNull
    private Integer sale;

}



