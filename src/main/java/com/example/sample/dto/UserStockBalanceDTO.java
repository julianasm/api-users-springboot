package com.example.sample.dto;


import com.example.sample.models.UserOrders;
import com.example.sample.models.UserStockBalance;
import com.example.sample.models.UserStockBalanceId;
import com.example.sample.models.Users;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserStockBalanceDTO {
    private Long id_user;

    private Long id_stock;

    private String stock_symbol;

    private String stock_name;

    private Long volume;

    public UserStockBalanceDTO(Long id_user, Long id_stock, String stock_symbol, String stock_name, Long volume) {
        this.id_user = id_user;
        this.id_stock = id_stock;
        this.stock_symbol = stock_symbol;
        this.stock_name = stock_name;
        this.volume = volume;
    }

    public UserStockBalanceDTO() {
    }

    public UserStockBalance transformaParaObjeto(Users users){
        return new UserStockBalance( new UserStockBalanceId(users, id_stock),
                stock_symbol,
                stock_name,
                volume);
    }

}
