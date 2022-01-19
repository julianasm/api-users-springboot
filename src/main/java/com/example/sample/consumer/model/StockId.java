package com.example.sample.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockId {

    private Long id;
    private String stock_symbol;
    private String stock_name;
}
