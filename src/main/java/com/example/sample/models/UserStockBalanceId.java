package com.example.sample.models;

import com.example.sample.models.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserStockBalanceId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users users;
    private Long id_stock;

    public UserStockBalanceId() {
    }

    public UserStockBalanceId(Users users, Long id_stock) {
        this.users = users;
        this.id_stock = id_stock;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Long getId_stock() {
        return id_stock;
    }

    public void setId_stock(Long id_stock) {
        this.id_stock = id_stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStockBalanceId that = (UserStockBalanceId) o;
        return Objects.equals(users, that.users) && Objects.equals(id_stock, that.id_stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, id_stock);
    }
}
