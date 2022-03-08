package com.example.sample.consumer.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@NoArgsConstructor
public class StocksDto {

    private Long id;
    private Double bid_min;
    private Double bid_max;
    private Double ask_min;
    private Double ask_max;

    public StocksDto(Long id, Double bid_min, Double bid_max, Double ask_min, Double ask_max) {
        this.id = id;
        this.bid_min = bid_min;
        this.bid_max = bid_max;
        this.ask_min = ask_min;
        this.ask_max = ask_max;

    }
}
