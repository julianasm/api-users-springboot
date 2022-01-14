package com.example.sample.resources;


import com.example.sample.models.UserStockBalance;
import com.example.sample.repository.UserStockBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class UserStockBalanceResources {

    @Autowired
    UserStockBalanceRepository userStockBalanceRepository;

    @GetMapping("/userStock")
    public List<UserStockBalance> listaUserStock() {
        return userStockBalanceRepository.findAll();
    }

    @PostMapping("/userStockItem")
    public UserStockBalance saveUserStock(@RequestBody UserStockBalance userStockBalance){
        return userStockBalanceRepository.save(userStockBalance);
    }


}
