package com.example.sample.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
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

    @Column(name="id_stock")
    private Long idStock;

    @Column(name="stock_symbol")
    private String stockSymbol;

    @Column(name="stock_name")
    private String stockName;

    private Long volume;

    private Integer type;

    private Integer status;

    private Double price;

    @Column(name="created_on")
    private Timestamp createdOn;

    @Column(name="updated_on")
    private Timestamp updatedOn;

    @Column(name="remaining_volume")
    private Long remainingVolume;

    public UserOrders(Users users, Long idStock, String stockSymbol, String stockName, Long volume, Integer type, Integer status, Double price, Long remainingVolume) {
        this.users = users;
        this.idStock = idStock;
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
        this.volume = volume;
        this.price = price;
        this.type = type;
        this.status = status;
        this.createdOn = Timestamp.valueOf(LocalDateTime.now());
        this.updatedOn = Timestamp.valueOf(LocalDateTime.now());
        this.remainingVolume = remainingVolume;
    }

    public UserOrders(){
        this.createdOn = Timestamp.valueOf(LocalDateTime.now());
        this.updatedOn = Timestamp.valueOf(LocalDateTime.now());
    }
}