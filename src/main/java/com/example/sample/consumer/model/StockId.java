package com.example.sample.consumer.model;

import com.fasterxml.jackson.databind.node.DoubleNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Data
@AllArgsConstructor
public class StockId {

    private Long id;
    private String stock_symbol;
    private String stock_name;
    private Double ask_min;
    private Double ask_max;
    private Double bid_min;
    private Double bid_max;
    private Timestamp created_on;
    private Timestamp updated_on;

    public StockId() {
    }

}
