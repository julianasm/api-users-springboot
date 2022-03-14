package com.example.sample.consumer.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@NoArgsConstructor
public class StocksDto {

    private Long id;
    private Double bidMin;
    private Double bidMax;
    private Double askMin;
    private Double askMax;

    public StocksDto(Long id, Double bidMin, Double bidMax, Double askMin, Double askMax) {
        this.id = id;
        this.bidMin = bidMin;
        this.bidMax = bidMax;
        this.askMin = askMin;
        this.askMax = askMax;

    }
}
