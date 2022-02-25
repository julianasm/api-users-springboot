package com.example.sample.resources;

import com.example.sample.consumer.DTO.StocksDto;
import com.example.sample.consumer.controller.StockController;
import com.example.sample.consumer.service.StockService;
import com.example.sample.models.UserOrders;
import com.example.sample.models.UserStockBalance;
import com.example.sample.models.UserStockBalanceId;
import com.example.sample.models.Users;

import com.example.sample.repository.UserOrdersRepository;
import com.example.sample.repository.UserStockBalanceRepository;
import com.example.sample.repository.UsersRepository;
import com.example.sample.serviceOrder.UpdateOrderDto;
import com.example.sample.serviceOrder.UserOrderDTO;
import com.example.sample.serviceOrder.UserOrderService;
import com.example.sample.serviceUsers.UsersDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class UserOrdersResources {

    private final StockService stockService;

    private final UsersRepository usersRepository;

    private final UserStockBalanceRepository userStockBalanceRepository;

    private final UserOrderService userOrderService;

    private final StockController stockController;

    private final UserOrdersRepository userOrdersRepository;

    @CrossOrigin
    @GetMapping("/orders")
    public List<UserOrders> listaPedidos(){
        return userOrdersRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/orders/{id}")
    public List<UserOrders> listaOrdemPorId(@PathVariable Long id){
        return userOrdersRepository.findByIdUser(id);
    }

    @CrossOrigin
    @PostMapping("/order-update/{status}")
    public ResponseEntity<UserOrders> updateOrder(@RequestBody UpdateOrderDto dto, @PathVariable("status") Integer status, @RequestHeader("Authorization") String token) throws Exception {
        try {
            System.out.println("chegou aqui");
            return ResponseEntity.ok().body(userOrderService.updateStatus(dto.getId(), status));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }



    @CrossOrigin
    @PostMapping("/new_order")
    public ResponseEntity<UserOrderDTO> salvar(@RequestBody UserOrderDTO dto, @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok().body(userOrderService.salvar(dto, token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

}