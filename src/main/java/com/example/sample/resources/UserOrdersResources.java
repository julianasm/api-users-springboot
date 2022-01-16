package com.example.sample.resources;


import com.example.sample.models.UserOrders;
import com.example.sample.repository.UserOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/newOrder")
    public UserOrders saveOrder(@RequestBody UserOrders userOrders){
        return userOrdersRepository.save(userOrders);
    }

}