package com.example.sample.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserOrders implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_user")
    private Users users;

    private Long id_stock;

    private String stock_symbol;

    private String stock_name;

    private Long volume;

    private Integer type;

    private Integer status;

    private Double price;

    private Timestamp created_on;

    private Timestamp updated_on;

    public UserOrders() {
        this.price = 0.0;
        this.created_on = Timestamp.valueOf(LocalDateTime.now());
        this.updated_on = Timestamp.valueOf(LocalDateTime.now());
    }

}
