package com.example.sample.consumer.controller;

import com.example.sample.consumer.model.StockId;
import com.example.sample.consumer.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stocks/{id}")
    public ResponseEntity<StockId> getStockbyId(@PathVariable Long id){

        StockId stockId = this.stockService.stockbyId(id);

        return ResponseEntity.ok(stockId);
    }

}
