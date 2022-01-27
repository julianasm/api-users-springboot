package com.example.sample.consumer.controller;

import com.example.sample.consumer.DTO.StocksDto;
import com.example.sample.consumer.model.StockId;
import com.example.sample.consumer.service.StockService;
import com.example.sample.models.UserOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stocks/{id}")
    public ResponseEntity<StocksDto> getStockbyId(@PathVariable Long id){

        StocksDto stocksDto = this.stockService.stockbyId(id);

        return ResponseEntity.ok(stocksDto);
    }

    @PutMapping("/updateStock")
    public ResponseEntity<StockId> updateStockbyId(){
        return null;

    }


}
