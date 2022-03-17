package com.example.sample.dto;

import com.example.sample.models.UserOrders;
import com.example.sample.models.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserOrderDTO {

    private Long idUser;

    private Long idStock;

    private String stockSymbol;

    private String stockName;

    private Long volume;

    private Integer type;

    private Integer status;

    private Double price;

    private Long remainingVolume;



    public UserOrders transformaParaObjeto(Users users){
        UserOrders userOrders = new UserOrders();
                userOrders.setUsers(users);
                userOrders.setIdStock(idStock);
                userOrders.setStockSymbol(stockSymbol);
                userOrders.setStockName(stockName);
                userOrders.setVolume(volume);
                userOrders.setType(type);
                userOrders.setStatus(status);
                userOrders.setPrice(price);
                userOrders.setRemainingVolume(remainingVolume);

                return userOrders;
    }
}


