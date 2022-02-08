package com.example.sample.consumer.service;

import com.example.sample.consumer.DTO.StocksDto;
import com.example.sample.consumer.model.StockId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StockService {

    @Autowired
    private WebClient webClient;

    StocksDto stocksDto;

    public StocksDto stockbyId(@PathVariable Long id, @RequestHeader("Authorization") String token) {

        Mono<StocksDto> monoStock = this.webClient
                .method(HttpMethod.GET)
                .uri("/stocks/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(StocksDto.class);

        StocksDto stocksDto =  monoStock.block();


        return stocksDto;
    }

    public void UpdateStockbyPrice(StocksDto stocksDto, String token) {
        Mono<StocksDto> monoStockPrice = this.webClient
                .method(HttpMethod.PUT)
                .uri("/update_stocks")
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(Mono.just(stocksDto), StocksDto.class)
                .retrieve()
                .bodyToMono(StocksDto.class);

        monoStockPrice.block();
    }
}
