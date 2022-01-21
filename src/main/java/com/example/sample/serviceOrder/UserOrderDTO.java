package com.example.sample.serviceOrder;

import com.example.sample.models.UserOrders;
import com.example.sample.models.Users;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserOrderDTO {

    private Long id_user;

    private Long id_stock;

    private String stock_symbol;

    private String stock_name;

    private Long volume;

    private Integer type;

    private Integer status;

    private Double price;

    private Long remaining_volume;


    public UserOrderDTO(){}

    public UserOrders transformaParaObjeto(Users users){
        return new UserOrders(
                users,
                id_stock,
                stock_symbol,
                stock_name,
                volume,
                type,
                status,
                price,
                remaining_volume);
    }
}


