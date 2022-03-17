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

    @Column(name="dollar_balance")
    private Double dollarBalance;

    private Boolean enabled = true;

    @Column(name="created_on")
    private Timestamp createdOn;

    @Column(name="updated_on")
    private Timestamp updatedOn;

    public Users () {
        this.enabled = true;
        this.createdOn = Timestamp.valueOf(LocalDateTime.now());
        this.updatedOn = Timestamp.valueOf(LocalDateTime.now());
    }

    public Users(Long id) {
        this.id = id;
        this.createdOn = Timestamp.valueOf(LocalDateTime.now());
        this.updatedOn = Timestamp.valueOf(LocalDateTime.now());
    }

    public Users(String username, String password, Double dollarBalance, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.dollarBalance = dollarBalance;
        this.enabled = enabled;
        this.createdOn = Timestamp.valueOf(LocalDateTime.now());
        this.updatedOn = Timestamp.valueOf(LocalDateTime.now());
    }
}

