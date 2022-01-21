package com.example.sample.models;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name="users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private Double dollar_balance;

    private Boolean enabled = true;

    private Timestamp created_on;

    private Timestamp updated_on;

    public Users () {

        this.enabled = true;
        this.created_on = Timestamp.valueOf(LocalDateTime.now());
        this.updated_on = Timestamp.valueOf(LocalDateTime.now());
    }

    public Users(String username, String password, Double dollar_balance) {
        this.username = username;
        this.password = password;
        this.dollar_balance = dollar_balance;
        this.created_on = Timestamp.valueOf(LocalDateTime.now());
        this.updated_on = Timestamp.valueOf(LocalDateTime.now());
    }
}

