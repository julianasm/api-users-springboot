package com.example.sample.resources;


import com.example.sample.dto.UserStockBalanceDTO;
import com.example.sample.models.UserStockBalance;
import com.example.sample.models.Users;
import com.example.sample.repository.UserStockBalanceRepository;
import com.example.sample.repository.UsersRepository;
import com.example.sample.service.UserStockBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api")
public class UserStockBalanceResources {

    @Autowired
    UserStockBalanceRepository userStockBalanceRepository;

    @Autowired
    UsersRepository usersRepository;


    private final UserStockBalanceService userStockBalanceService;

    @CrossOrigin
    @GetMapping("/user_stock/{id}")
    public List<UserStockBalance> listaUserStock(@PathVariable Long id) {
        return userStockBalanceRepository.findAllByIdUser(id);
    }


    @CrossOrigin
    @GetMapping("/user_stock")
    public List<UserStockBalance> listaUserStock() {
        return userStockBalanceRepository.findAll();
    }

    @CrossOrigin
    @PostMapping("/new-user-stock")
    public ResponseEntity<UserStockBalance> salvar(@RequestBody UserStockBalanceDTO dto) {
        Users users = usersRepository.findById(dto.getId_user()).orElseThrow();
        UserStockBalance userStockBalance = userStockBalanceService.findById(users, dto.getId_stock()).orElse(userStockBalanceService.salvar(dto.transformaParaObjeto(users)));
        return new ResponseEntity<>(userStockBalance, HttpStatus.CREATED);
    }

}
