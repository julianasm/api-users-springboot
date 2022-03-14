package com.example.sample.consumer.service;

import com.example.sample.consumer.dto.StockInfoDto;
import com.example.sample.consumer.dto.StocksDto;
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


    public StocksDto stockbyId(@PathVariable Long id, @RequestHeader("Authorization") String token) {

        Mono<StocksDto> monoStock = this.webClient
                .method(HttpMethod.GET)
                .uri("/stocks/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(StocksDto.class);

        return monoStock.block();

    }

    public StockInfoDto stockInfoById(@PathVariable Long id, @RequestHeader("Authorization") String token) {

        Mono<StockInfoDto> monoStock = this.webClient
                .method(HttpMethod.GET)
                .uri("/stock-info/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(StockInfoDto.class);

        return monoStock.block();


    }



    public StockInfoDto[] getAllStocks(@RequestHeader("Authorization") String token) {

        Mono<StockInfoDto[]> monoStock = this.webClient
                .method(HttpMethod.GET)
                .uri("/stocks")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(StockInfoDto[].class);

        return monoStock.block();

    }


    public void updateStockbyPrice(StocksDto stocksDto, String token) {
        Mono<StocksDto> monoStockPrice = this.webClient
                .method(HttpMethod.POST)
                .uri("/update_stocks")
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(Mono.just(stocksDto), StocksDto.class)
                .retrieve()
                .bodyToMono(StocksDto.class);

        monoStockPrice.block();
    }
}
