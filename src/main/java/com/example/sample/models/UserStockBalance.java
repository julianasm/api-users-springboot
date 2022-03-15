package com.example.sample.models;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="user_stock_balances")
public class UserStockBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserStockBalanceId id;

    @Column(name="stock_symbol")
    private String stockSymbol;

    @Column(name="stock_name")
    private String stockName;

    private Long volume;

    @CreationTimestamp
    @Column(name="created_on")
    private Timestamp createdOn;

    @UpdateTimestamp
    @Column(name="updated_on")
    private Timestamp updatedOn;



    public UserStockBalance() {
        this.createdOn = Timestamp.valueOf(LocalDateTime.now());
        this.updatedOn = Timestamp.valueOf(LocalDateTime.now());
    }



    public UserStockBalance(UserStockBalanceId id , String stockSymbol, String stockName, Long volume) {
        this.id = id;

        this.stockSymbol = stockSymbol;

        this.stockName = stockName;

        this.volume = volume;

        this.createdOn = Timestamp.valueOf(LocalDateTime.now());
        this.updatedOn = Timestamp.valueOf(LocalDateTime.now());
    }
}
