package com.example.sample.consumer.controller;

import com.example.sample.consumer.dto.StockInfoDto;
import com.example.sample.consumer.dto.StocksDto;
import com.example.sample.consumer.model.StockId;
import com.example.sample.consumer.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:8081/", "http://localhost:8083"})
@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stocks/{id}")
    public ResponseEntity<StocksDto> getStockbyId(@PathVariable Long id, @RequestHeader("Authorization") String token){

        StocksDto stocksDto = this.stockService.stockbyId(id, token);

        return ResponseEntity.ok(stocksDto);
    }

    @GetMapping("/stock-info/{id}")
    public ResponseEntity<StockInfoDto> getStockInfoById(@PathVariable Long id, @RequestHeader("Authorization") String token){

        StockInfoDto stockInfoDto = this.stockService.stockInfoById(id, token);

        return ResponseEntity.ok(stockInfoDto);
    }



    @GetMapping("/stocks")
    public ResponseEntity<StockInfoDto[]> getAllStocks(@RequestHeader("Authorization") String token){

        StockInfoDto[] stockInfoDtos = this.stockService.getAllStocks(token);

        return  ResponseEntity.ok(stockInfoDtos);
    }


    @PostMapping ("/updateStock")
    public ResponseEntity<StockId> updateStockbyId(@RequestHeader("Authorization") String token){
        return null;

    }


}
