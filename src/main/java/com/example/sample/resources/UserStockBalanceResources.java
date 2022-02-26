package com.example.sample.resources;


import com.example.sample.models.UserStockBalance;
import com.example.sample.repository.UserStockBalanceRepository;
import com.example.sample.repository.UsersRepository;
import com.example.sample.service.UserStockBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<UserStockBalance> userStockBlance = userStockBalanceRepository.findAllByIdUser(id);
        return userStockBlance;
    }


    @CrossOrigin
    @GetMapping("/user_stock")
    public List<UserStockBalance> listaUserStock() {
        return userStockBalanceRepository.findAll();
    }

//    @CrossOrigin
//    @PostMapping("/newUserStock")
//    public ResponseEntity<UserStockBalance> salvar(@RequestBody UserStockBalanceDTO dto) {
//
//        Users users = userStockBalanceService.findById(, dto.getId_stock());
//        UserStockBalance userStockBalance = userStockBalanceService.salvar(dto.transformaParaObjeto(users));
//        return new ResponseEntity<>(userStockBalance, HttpStatus.CREATED);
//    }

}
