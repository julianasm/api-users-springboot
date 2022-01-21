package com.example.sample.serviceUsers;

import com.example.sample.models.UserOrders;
import com.example.sample.models.Users;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class UsersDTO {

    private String username;

    private String password;

    private Double dollar_balance;

    private Boolean enabled = true;

    public UsersDTO(){}


    public Users transformaParaObjeto(){
        return new Users(
                username,
                password,
                dollar_balance
                );
    }
}
