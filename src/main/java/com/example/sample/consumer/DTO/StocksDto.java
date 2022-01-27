package com.example.sample.consumer.DTO;


import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class StocksDto {

    private Long id;
    private Double bid_min_price;
    private Double bid_max_price;
    private Double ask_min_price;
    private Double ask_max_price;

    public StocksDto(){
    }

    public StocksDto(Long id, Double bid_min_price, Double bid_max_price, Double ask_min_price, Double ask_max_price) {
        this.id = id;
        this.bid_min_price = bid_min_price;
        this.bid_max_price = bid_max_price;
        this.ask_min_price = ask_min_price;
        this.ask_max_price = ask_max_price;
    }
}
