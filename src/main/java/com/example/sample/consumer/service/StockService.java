package com.example.sample.consumer.service;

import com.example.sample.consumer.model.StockId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StockService {

    @Autowired
    private WebClient webClient;

    public StockId stockbyId(Long id) {

        Mono<StockId> monoStock = this.webClient
                .method(HttpMethod.GET)
                .uri("/stocks/{id}", id)
                .retrieve()
                .bodyToMono(StockId.class);

        StockId stockId =  monoStock.block();

        Mono<StockId> monoStockPrice = this.webClient
                .method(HttpMethod.PUT)
                .uri("/updateStocks/{id}", id)
                .retrieve()
                .bodyToMono(StockId.class);

        return stockId;
    }
}
