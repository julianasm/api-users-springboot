package com.example.sample.resources;

import com.example.sample.handleerror.NotFoundException;
import com.example.sample.models.Users;
import com.example.sample.dto.SaveUserDto;
import com.example.sample.dto.UsersDTO;
import com.example.sample.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:8081/")
@RestController
@RequestMapping(value="/api")
@RequiredArgsConstructor
public class UsersResources{


    private final UsersService usersService;


    @GetMapping("/users")
    public List<Users> listaUsers(){
        return usersService.listAll();
    }


    @GetMapping("/users-id")
    public List<UsersDTO> listUsersId() throws InterruptedException {
        Thread.sleep(3000);

        return usersService.listAllDto();
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUser(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(usersService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }



    @GetMapping("/users/username/{username}")
    public ResponseEntity<UsersDTO> findByUsername(@PathVariable("username") String username) throws NotFoundException {
        try {
            return ResponseEntity.ok().body(usersService.findByUsername(username));
        } catch ( Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/new_user")
    public ResponseEntity<SaveUserDto> salvar(@RequestBody SaveUserDto dto) {
        return ResponseEntity.ok().body(usersService.salvar(dto));
    }

}