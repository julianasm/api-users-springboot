package com.example.sample.resources;


import com.example.sample.models.UserStockBalance;
import com.example.sample.models.UserStockBalanceId;
import com.example.sample.models.Users;
import com.example.sample.repository.UserStockBalanceRepository;
import com.example.sample.repository.UsersRepository;
import com.example.sample.serviceStockBalance.UserStockBalanceDTO;
import com.example.sample.serviceStockBalance.UserStockBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value="/api")
public class UserStockBalanceResources {

    @Autowired
    UserStockBalanceRepository userStockBalanceRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserStockBalanceService userStockBalanceService;

    @CrossOrigin
    @GetMapping("/user_stock/{id}/{id_stock}")
    public <List> UserStockBalance listaUserStock(@PathVariable Users id, @PathVariable Long id_stock) {
        UserStockBalance userStockBlance = userStockBalanceRepository.findAllById(new UserStockBalanceId(id, id_stock));
        return userStockBlance;
    }


    @CrossOrigin
    @GetMapping("/user_stock")
    public List<UserStockBalance> listaUserStock() {
        return userStockBalanceRepository.findAll();
    }

    @CrossOrigin
    @PostMapping("/newUserStock")
    public ResponseEntity<UserStockBalance> salvar(@RequestBody UserStockBalanceDTO dto) {
        Users users = usersRepository.findById(dto.getId_user()).orElseThrow();
        UserStockBalance userStockBalance = userStockBalanceService.salvar(dto.transformaParaObjeto(users));
        return new ResponseEntity<>(userStockBalance, HttpStatus.CREATED);
    }

}
