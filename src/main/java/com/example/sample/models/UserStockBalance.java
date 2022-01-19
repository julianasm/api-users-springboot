package com.example.sample.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
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

    private String stock_symbol;

    private String stock_name;

    private Long volume;

    private Timestamp created_on;

    private Timestamp updated_on;



    public UserStockBalance() {
        this.created_on = Timestamp.valueOf(LocalDateTime.now());
        this.updated_on = Timestamp.valueOf(LocalDateTime.now());
    }



    public UserStockBalance(UserStockBalanceId id , String stock_symbol, String stock_name, Long volume) {
        this.id = id;

        this.stock_symbol = stock_symbol;

        this.stock_name = stock_name;

        this.volume = volume;

        this.created_on = Timestamp.valueOf(LocalDateTime.now());
        this.updated_on = Timestamp.valueOf(LocalDateTime.now());
    }
}
