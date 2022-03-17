package com.example.sample.dto;


import com.example.sample.models.UserStockBalance;
import com.example.sample.models.UserStockBalanceId;
import com.example.sample.models.Users;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserStockBalanceDTO {
    private Long idUser;

    private Long idStock;

    private String stockSymbol;

    private String stockName;

    private Long volume;

    public UserStockBalanceDTO(Long idUser, Long idStock, String stockSymbol, String stockName, Long volume) {
        this.idUser = idUser;
        this.idStock = idStock;
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
        this.volume = volume;
    }

    public UserStockBalanceDTO() {
    }

    public UserStockBalance transformaParaObjeto(Users users){
        return new UserStockBalance( new UserStockBalanceId(users, idStock),
                stockSymbol,
                stockName,
                volume);
    }

    public UserStockBalanceDTO(UserStockBalance userStockBalance){
        this.idUser = userStockBalance.getId().getUsers().getId();
        this.idStock = userStockBalance.getId().getIdStock();
        this.stockSymbol = userStockBalance.getStockSymbol();
        this.stockName = userStockBalance.getStockName();
        this.volume = userStockBalance.getVolume();
    }

}
