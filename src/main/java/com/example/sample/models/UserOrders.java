package com.example.sample.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="user_orders")
public class UserOrders implements Serializable{

    private static final long serialVersionUID = 1L;


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

    private Long remaining_volume;

    public UserOrders(Users users, Long id_stock, String stock_symbol, String stock_name, Long volume, Integer type, Integer status, Double price, Long remaining_volume) {
        this.users = users;
        this.id_stock = id_stock;
        this.stock_symbol = stock_symbol;
        this.stock_name = stock_name;
        this.volume = volume;
        this.price = price;
        this.type = type;
        this.status = status;
        this.created_on = Timestamp.valueOf(LocalDateTime.now());
        this.updated_on = Timestamp.valueOf(LocalDateTime.now());
        this.remaining_volume = remaining_volume;
    }

    public UserOrders(){
        this.created_on = Timestamp.valueOf(LocalDateTime.now());
        this.updated_on = Timestamp.valueOf(LocalDateTime.now());
    }


}