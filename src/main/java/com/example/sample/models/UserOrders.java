package com.example.sample.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
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
    @CreationTimestamp
    private Timestamp createdOn;

    @UpdateTimestamp
    @Column(name="updated_on")

    private Timestamp updatedOn;

    @Column(name="remaining_volume")
    private Long remainingVolume;

}