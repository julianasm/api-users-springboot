package com.example.sample.consumer.dto;

import lombok.Data;



@Data
public class StockInfoDto {

    private Long id;
    private String stockSymbol;
    private String stockName;

    public StockInfoDto() {
    }

    public StockInfoDto(Long id, String stockSymbol, String stockName) {
        this.id = id;
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
    }
}
