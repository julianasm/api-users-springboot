package com.example.sample.resources;

import com.example.sample.models.Users;
import com.example.sample.repository.UsersRepository;
import com.example.sample.serviceUsers.SaveUserDto;
import com.example.sample.serviceUsers.UsersDTO;
import com.example.sample.serviceUsers.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value="/api")
@RequiredArgsConstructor
public class UsersResources{

    @Autowired
    UsersRepository usersRepository;

    private final UsersService usersService;

    @GetMapping("/users")
    public List<Users> listaUsers(){
        return usersRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/users/{id}")
    public Optional<Users> getUser(@PathVariable("id") Long id) {
        return usersRepository.findById(id);
    }

//    @CrossOrigin
//    @PostMapping("/newUserStock")
//    public ResponseEntity<UserStockBalance> salvar(@RequestBody UserStockBalanceDTO dto) {
//        Users users = usersRepository.findById(dto.getId_user()).orElseThrow();
//        UserStockBalance userStockBalance = userStockBalanceService.salvar(dto.transformaParaObjeto(users));
//        return new ResponseEntity<>(userStockBalance, HttpStatus.CREATED);
//    }

    @CrossOrigin
    @GetMapping("/users/username/{username}")
    public ResponseEntity<UsersDTO> findByUsername(@PathVariable("username") String username) throws Exception {
        try {
            return ResponseEntity.ok().body(usersService.findByUsername(username));
        } catch ( Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin
    @PostMapping("/new_user")
    public ResponseEntity<SaveUserDto> salvar(@RequestBody SaveUserDto dto) {
        return ResponseEntity.ok().body(usersService.salvar(dto));
    }

}