package com.example.sample.resources;

import com.example.sample.models.UserOrders;
import com.example.sample.models.Users;
import com.example.sample.repository.UserOrdersRepository;
import com.example.sample.repository.UsersRepository;
import com.example.sample.serviceOrder.UserOrderDTO;
import com.example.sample.serviceOrder.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserOrdersResources {

    @Autowired
    UserOrdersRepository userOrdersRepository;

    @GetMapping("/orders")
    public List<UserOrders> listaPedidos(){
        return userOrdersRepository.findAll();
    }

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserOrderService userOrderService;

    @PostMapping("/newOrder")
    public ResponseEntity<UserOrders> salvar(@RequestBody UserOrderDTO dto) {
        Users users = usersRepository.findById(dto.getId_user()).orElseThrow();
        UserOrders userOrders = userOrderService.salvar(dto.transformaParaObjeto(users));
        return new ResponseEntity<>(userOrders, HttpStatus.CREATED);
    }

}