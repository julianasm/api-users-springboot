package com.example.sample.models;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Table(name="user_orders")
@IdClass(UserStockBalanceId.class)
public class UserStockBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="id_user")
    @Id
    private Users users;

    @Id
    private Long id_stock;

    private String stock_symbol;

    private String stock_name;

    private Long volume;

    private Timestamp created_on;

    private Timestamp updated_on;



    public UserStockBalance() {
        this.created_on = Timestamp.valueOf(LocalDateTime.now());
        this.updated_on = Timestamp.valueOf(LocalDateTime.now());
    }




}
