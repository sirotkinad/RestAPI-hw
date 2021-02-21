package com.mycompany.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name = "purchase")
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @NotNull
    private Date orderdate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "seller", referencedColumnName = "id")
    private Shop shop;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer", referencedColumnName = "id")
    private Customer customer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "book", referencedColumnName = "id")
    private Book book;

    @NotNull
    private Integer quantity;
    @NotNull
    private Double ordersum;

}
