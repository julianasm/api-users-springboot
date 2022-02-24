package com.example.sample.consumer.DTO;

import lombok.Data;



@Data
public class StockInfoDto {

    private Long id;
    private String stock_symbol;
    private String stock_name;

    public StockInfoDto() {
    }

    public StockInfoDto(Long id, String stock_symbol, String stock_name) {
        this.id = id;
        this.stock_symbol = stock_symbol;
        this.stock_name = stock_name;
    }
}
