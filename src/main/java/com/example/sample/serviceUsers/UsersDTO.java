package com.example.sample.serviceUsers;

import com.example.sample.models.UserOrders;
import com.example.sample.models.Users;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class UsersDTO {

    private Long id;

    public UsersDTO(Long id){
        this.id = id;
    }

    public UsersDTO() {
    }

    public Users transformaParaObjeto(){
        return new Users(
                    id
                );
    }
}
