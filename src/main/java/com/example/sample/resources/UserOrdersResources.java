package com.example.sample.resources;

import com.example.sample.consumer.controller.StockController;
import com.example.sample.consumer.service.StockService;
import com.example.sample.handleerror.NotUpdatedException;
import com.example.sample.models.UserOrders;

import com.example.sample.repository.UserOrdersRepository;
import com.example.sample.repository.UserStockBalanceRepository;
import com.example.sample.repository.UsersRepository;
import com.example.sample.dto.UpdateOrderDto;
import com.example.sample.dto.UserOrderDTO;
import com.example.sample.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public Page<UserOrders> listaPedidos(@RequestParam int pageSize, @RequestParam int pageNumber){
        return userOrderService.findOrdersPage(pageSize, pageNumber);
    }

    @CrossOrigin
    @GetMapping("/orders/{id}")
    public Page<UserOrders> listaOrdemPorId(@PathVariable Long id, @RequestParam int pageNumber, int pageSize){
        return userOrderService.findOrdersPageById(id, pageSize, pageNumber);
    }

    @CrossOrigin
    @PostMapping("/order-update/{status}")
    public ResponseEntity<UserOrders> updateOrder(@RequestBody UpdateOrderDto dto, @PathVariable("status") Integer status, @RequestHeader("Authorization") String token) throws NotUpdatedException {
        try {
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