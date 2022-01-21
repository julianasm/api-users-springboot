package com.example.sample.resources;


import com.example.sample.models.UserStockBalance;
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

@RestController
@RequestMapping(value="/api")
public class UserStockBalanceResources {

    @Autowired
    UserStockBalanceRepository userStockBalanceRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserStockBalanceService userStockBalanceService;

    @GetMapping("/userStock")
    public List<UserStockBalance> listaUserStock() {
        return userStockBalanceRepository.findAll();
    }

    @PostMapping("/newUserStock")
    public ResponseEntity<UserStockBalance> salvar(@RequestBody UserStockBalanceDTO dto) {
        Users users = usersRepository.findById(dto.getId_user()).orElseThrow();
        UserStockBalance userStockBalance = userStockBalanceService.salvar(dto.transformaParaObjeto(users));
        return new ResponseEntity<>(userStockBalance, HttpStatus.CREATED);
    }

}
