package com.example.sample.models;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserStockBalanceId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users users;
    @Column(name="id_stock")
    private Long idStock;

    public UserStockBalanceId() {
    }

    public UserStockBalanceId(Users users, Long idStock) {
        this.users = users;
        this.idStock = idStock;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Long getIdStock() {
        return idStock;
    }

    public void setIdStock(Long idStock) {
        this.idStock = idStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStockBalanceId that = (UserStockBalanceId) o;
        return Objects.equals(users, that.users) && Objects.equals(idStock, that.idStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, idStock);
    }
}
