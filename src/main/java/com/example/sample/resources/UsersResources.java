package com.example.sample.resources;

import com.example.sample.models.Users;
import com.example.sample.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class UsersResources{

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/usuarios")
    public List<Users> listaUsers(){
        return usersRepository.findAll();
    }

    @PostMapping("/usuario")
    public Users saveUsers(@RequestBody Users users) {
        return usersRepository.save(users);
    }

}