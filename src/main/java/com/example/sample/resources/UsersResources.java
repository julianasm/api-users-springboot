package com.example.sample.resources;

import com.example.sample.models.Users;
import com.example.sample.repository.UsersRepository;
import com.example.sample.serviceStockBalance.UserStockBalanceDTO;
import com.example.sample.serviceStockBalance.UserStockBalanceService;
import com.example.sample.serviceUsers.UsersDTO;
import com.example.sample.serviceUsers.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class UsersResources{

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersService usersService;

    @GetMapping("/users")
    public List<Users> listaUsers(){
        return usersRepository.findAll();
    }

    @PostMapping("/newUser")
    public ResponseEntity<Users> salvar(@RequestBody UsersDTO dto) {
        Users users = dto.transformaParaObjeto();
        users = usersRepository.save(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

}