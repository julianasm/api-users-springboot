package com.example.sample.consumer.model;

import com.fasterxml.jackson.databind.node.DoubleNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @CreationTimestamp
    private Timestamp created_on;
    @UpdateTimestamp
    private Timestamp updated_on;

    public StockId() {
        this.created_on = Timestamp.valueOf(LocalDateTime.now());
        this.updated_on = Timestamp.valueOf(LocalDateTime.now());
    }

}
