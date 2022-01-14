package com.example.sample.models;

import com.example.sample.models.Users;
import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.util.Objects;

public class UserStockBalanceId implements Serializable {

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
    public int hashCode() {
        return Objects.hash(users, id_stock);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
        final UserStockBalanceId other = (UserStockBalanceId) obj;
        if (this.users != other.users){
            return false;
        }
        if (this.id_stock != other.id_stock){
            return false;
        }
        return true;
    }
}
